package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.application.domain.service.booking.aggregate.BookingAggregateDomainService;
import de.dhbw.ems.application.domain.service.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.application.domain.service.booking.reference.BookingReferenceDomainService;
import de.dhbw.ems.application.domain.service.user.UserDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.mediator.colleage.BookingColleague;
import de.dhbw.ems.application.mediator.service.impl.BookingService;
import de.dhbw.ems.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingOperationService extends BookingColleague implements BookingService {

    private final UserDomainService userDomainService;
    private final FinancialLedgerService financialLedgerService;
    private final BookingAggregateDomainService bookingAggregateDomainService;
    private final BookingReferenceDomainService bookingReferenceDomainService;

    public BookingOperationService(
            final ConcreteApplicationMediator mediator,
            final UserDomainService userDomainService,
            final FinancialLedgerService financialLedgerService,
            final BookingAggregateDomainService bookingAggregateDomainService,
            final BookingReferenceDomainService bookingReferenceDomainService
            ) {
        super(mediator, bookingAggregateDomainService, bookingReferenceDomainService);
        this.userDomainService = userDomainService;
        this.financialLedgerService = financialLedgerService;
        this.bookingAggregateDomainService = bookingAggregateDomainService;
        this.bookingReferenceDomainService = bookingReferenceDomainService;
    }

    public Optional<BookingAggregate> find(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId){
        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerService.find(userId, financialLedgerAggregateId);
        if (!optionalFinancialLedgerAggregate.isPresent()) return Optional.empty();
        return optionalFinancialLedgerAggregate.get().getBookingAggregates().stream().filter(b -> b.getFinancialLedgerId().equals(financialLedgerAggregateId) && b.getId().equals(bookingAggregateId)).findFirst();
    }

    @Transactional
    public Optional<BookingAggregate> create(UUID userId, UUID financialLedgerAggregateId, BookingAggregateAttributeData attributeData){
        Optional<User> optionalUser = userDomainService.findById(userId);
        if (!optionalUser.isPresent()) return Optional.empty();
        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerService.find(userId, financialLedgerAggregateId);
        if (!optionalFinancialLedgerAggregate.isPresent()) return Optional.empty();
        Optional<BookingAggregate> optionalBookingAggregate = bookingAggregateDomainService.createByAttributeData(optionalUser.get(), optionalFinancialLedgerAggregate.get(), attributeData);
        if (!optionalBookingAggregate.isPresent()) return Optional.empty();
        return optionalBookingAggregate;
    }

    public boolean exists(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId){
        return find(userId, financialLedgerAggregateId, bookingAggregateId).isPresent();
    }

    @Transactional
    public boolean delete(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId){
        Optional<BookingAggregate> optionalBooking = find(userId, financialLedgerAggregateId, bookingAggregateId);
        if (optionalBooking.isPresent()) {
            onDeleteBooking(optionalBooking.get());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean referenceUser(UUID id, UUID financialLedgerAggregateId, UUID bookingAggregateId, UUID referenceUserId){
        Optional<User> optionalReferenceUser = userDomainService.findById(referenceUserId);
        if (optionalReferenceUser.isPresent() && financialLedgerService.hasUserPermission(referenceUserId, financialLedgerAggregateId)){
            Optional<BookingAggregate> optionalBooking = find(id, financialLedgerAggregateId, bookingAggregateId);
            if (optionalBooking.isPresent()){
                getMediator().onReferenceUserToBooking(optionalReferenceUser.get(), optionalBooking.get(), this);
                onReferenceUserToBooking(optionalReferenceUser.get(), optionalBooking.get());
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean deleteUserReference(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId, UUID referenceUserId){
        Optional<User> optionalReferenceUser = userDomainService.findById(referenceUserId);
        if (optionalReferenceUser.isPresent()){
            Optional<BookingAggregate> optionalBooking = find(userId, financialLedgerAggregateId, bookingAggregateId);
            if (optionalBooking.isPresent()) {
                User user = optionalReferenceUser.get();
                BookingAggregate bookingAggregate = optionalBooking.get();
                if (bookingReferenceDomainService.exists(user.getId(), bookingAggregate.getId())){
                    getMediator().onDeleteReferenceUserToBooking(user, bookingAggregate, this);
                    onDeleteReferenceUserToBooking(user, bookingAggregate);
                    return true;
                }
            }
        }
        return false;
    }

}
