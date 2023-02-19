package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.application.booking.BookingAttributeData;
import de.dhbw.ems.application.booking.BookingDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.mediator.colleage.BookingColleague;
import de.dhbw.ems.application.mediator.service.impl.BookingService;
import de.dhbw.ems.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.ems.application.user.UserDomainService;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookingOperationService extends BookingColleague implements BookingService {

    private final UserDomainService userDomainService;
    private final FinancialLedgerService financialLedgerService;
    private final BookingDomainService bookingDomainService;

    public BookingOperationService(
            final ConcreteApplicationMediator mediator,
            final UserDomainService userDomainService,
            final FinancialLedgerService financialLedgerService,
            final BookingDomainService bookingDomainService
            ) {
        super(mediator, bookingDomainService);
        this.userDomainService = userDomainService;
        this.financialLedgerService = financialLedgerService;
        this.bookingDomainService = bookingDomainService;
    }

    public Optional<Booking> find(UUID userId, UUID financialLedgerId, UUID bookingId){
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerService.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        return optionalFinancialLedger.get().getBookings().stream().filter(b -> b.getId().equals(bookingId)).findFirst();
    }

    public Optional<Booking> create(UUID userId, UUID financialLedgerId, BookingAttributeData attributeData){
        Optional<User> optionalUser = userDomainService.findById(userId);
        if (!optionalUser.isPresent()) return Optional.empty();
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerService.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        Optional<Booking> optionalBooking = bookingDomainService.createByAttributeData(optionalUser.get(), optionalFinancialLedger.get(), attributeData);
        if (!optionalBooking.isPresent()) return Optional.empty();
        return find(userId, financialLedgerId, optionalBooking.get().getId());
    }

    public boolean exists(UUID userId, UUID financialLedgerId, UUID bookingId){
        return find(userId, financialLedgerId, bookingId).isPresent();
    }

    public boolean delete(UUID userId, UUID financialLedgerId, UUID bookingId){
        Optional<Booking> optionalBooking = find(userId, financialLedgerId, bookingId);
        if (optionalBooking.isPresent()) {
            getMediator().onDeleteBooking(optionalBooking.get(), this);
            onDeleteBooking(optionalBooking.get());
            return true;
        }
        return false;
    }

    public boolean referenceUser(UUID id, UUID financialLedgerId, UUID bookingId, UUID referenceUserId){
        Optional<User> optionalReferenceUser = userDomainService.findById(referenceUserId);
        if (optionalReferenceUser.isPresent() && financialLedgerService.hasUserPermission(referenceUserId, financialLedgerId)){
            Optional<Booking> optionalBooking = find(id, financialLedgerId, bookingId);
            if (optionalBooking.isPresent()){
                getMediator().onReferenceUserToBooking(optionalReferenceUser.get(), optionalBooking.get(), this);
                onReferenceUserToBooking(optionalReferenceUser.get(), optionalBooking.get());
                return true;
            }
        }
        return false;
    }

    public boolean deleteUserReference(UUID id, UUID financialLedgerId, UUID bookingId){
        Optional<User> optionalReferenceUser = userDomainService.findById(id);
        if (optionalReferenceUser.isPresent()){
            Optional<Booking> optionalBooking = find(id, financialLedgerId, bookingId);
            if (optionalBooking.isPresent()) {
                User user = optionalReferenceUser.get();
                Booking booking = optionalBooking.get();
                if (booking.getReferencedUsers().contains(user) || user.getReferencedBookings().contains(booking)){
                    getMediator().onDeleteReferenceUserToBooking(user, booking, this);
                    onDeleteReferenceUserToBooking(user, booking);
                    return true;
                }
            }
        }
        return false;
    }

}
