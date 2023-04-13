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
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
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

    public Optional<BookingAggregate> find(UUID userId, UUID financialLedgerId, UUID bookingAggregateId){
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerService.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        return optionalFinancialLedger.get().getBookingAggregates().stream().filter(b -> b.getFinancialLedgerId().equals(financialLedgerId) && b.getId().equals(bookingAggregateId)).findFirst();
    }

    @Transactional
    public Optional<BookingAggregate> create(UUID userId, UUID financialLedgerId, BookingAggregateAttributeData attributeData){
        Optional<User> optionalUser = userDomainService.findById(userId);
        if (!optionalUser.isPresent()) return Optional.empty();
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerService.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        Optional<BookingAggregate> optionalBookingAggregate = bookingAggregateDomainService.createByAttributeData(optionalUser.get(), optionalFinancialLedger.get(), attributeData);
        if (!optionalBookingAggregate.isPresent()) return Optional.empty();
        return optionalBookingAggregate;
    }

    public boolean exists(UUID userId, UUID financialLedgerId, UUID bookingAggregateId){
        return find(userId, financialLedgerId, bookingAggregateId).isPresent();
    }

    @Transactional
    public boolean delete(UUID userId, UUID financialLedgerId, UUID bookingAggregateId){
        Optional<BookingAggregate> optionalBooking = find(userId, financialLedgerId, bookingAggregateId);
        if (optionalBooking.isPresent()) {
            onDeleteBooking(optionalBooking.get());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean referenceUser(UUID id, UUID financialLedgerId, UUID bookingAggregateId, UUID referenceUserId){
        Optional<User> optionalReferenceUser = userDomainService.findById(referenceUserId);
        if (optionalReferenceUser.isPresent() && financialLedgerService.hasUserPermission(referenceUserId, financialLedgerId)){
            Optional<BookingAggregate> optionalBooking = find(id, financialLedgerId, bookingAggregateId);
            if (optionalBooking.isPresent()){
                getMediator().onReferenceUserToBooking(optionalReferenceUser.get(), optionalBooking.get(), this);
                onReferenceUserToBooking(optionalReferenceUser.get(), optionalBooking.get());
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean deleteUserReference(UUID userId, UUID financialLedgerId, UUID bookingAggregateId, UUID referenceUserId){
        Optional<User> optionalReferenceUser = userDomainService.findById(referenceUserId);
        if (optionalReferenceUser.isPresent()){
            Optional<BookingAggregate> optionalBooking = find(userId, financialLedgerId, bookingAggregateId);
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
