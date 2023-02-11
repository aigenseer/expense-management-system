package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.application.booking.BookingApplicationService;
import de.dhbw.ems.application.booking.BookingAttributeData;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.mediator.colleage.BookingColleague;
import de.dhbw.ems.application.mediator.service.impl.BookingServicePort;
import de.dhbw.ems.application.user.UserApplicationService;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookingOperationService extends BookingColleague implements BookingServicePort {

    private final ConcreteApplicationMediator mediator;
    private final UserApplicationService userApplicationService;
    private final FinancialLedgerOperationService financialLedgerOperationService;
    private final BookingApplicationService bookingApplicationService;

    public BookingOperationService(
            final ConcreteApplicationMediator mediator,
            final UserApplicationService userApplicationService,
            final FinancialLedgerOperationService financialLedgerOperationService,
            final BookingApplicationService bookingApplicationService
            ) {
        super(mediator, bookingApplicationService);
        this.mediator = mediator;
        this.userApplicationService = userApplicationService;
        this.financialLedgerOperationService = financialLedgerOperationService;
        this.bookingApplicationService = bookingApplicationService;
    }

    public Optional<Booking> find(UUID userId, UUID financialLedgerId, UUID bookingId){
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerOperationService.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        return optionalFinancialLedger.get().getBookings().stream().filter(b -> b.getId().equals(bookingId)).findFirst();
    }

    public Optional<Booking> create(UUID userId, UUID financialLedgerId, BookingAttributeData attributeData){
        Optional<User> optionalUser = userApplicationService.findById(userId);
        if (!optionalUser.isPresent()) return Optional.empty();
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerOperationService.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        Optional<Booking> optionalBooking = bookingApplicationService.createByAttributeData(optionalUser.get(), optionalFinancialLedger.get(), attributeData);
        if (!optionalBooking.isPresent()) return Optional.empty();
        mediator.onCreateBooking(optionalUser.get(), optionalFinancialLedger.get(), optionalBooking.get(), this);
        return find(userId, financialLedgerId, optionalBooking.get().getId());
    }

    public boolean exists(UUID userId, UUID financialLedgerId, UUID bookingId){
        return find(userId, financialLedgerId, bookingId).isPresent();
    }

    public boolean delete(UUID userId, UUID financialLedgerId, UUID bookingId){
        Optional<Booking> optionalBooking = find(userId, financialLedgerId, bookingId);
        if (optionalBooking.isPresent()) {
            mediator.onDeleteBooking(optionalBooking.get(), this);
            onDeleteBooking(optionalBooking.get());
            return true;
        }
        return false;
    }

    public boolean referenceUser(UUID id, UUID financialLedgerId, UUID bookingId, UUID referenceUserId){
        Optional<User> optionalReferenceUser = userApplicationService.findById(referenceUserId);
        if (optionalReferenceUser.isPresent()){
            Optional<Booking> optionalBooking = find(id, financialLedgerId, bookingId);
            if (optionalBooking.isPresent()){
                mediator.onReferenceUserToBooking(optionalReferenceUser.get(), optionalBooking.get(), this);
                onReferenceUserToBooking(optionalReferenceUser.get(), optionalBooking.get());
                return true;
            }
        }
        return false;
    }

    public boolean deleteUserReference(UUID id, UUID financialLedgerId, UUID bookingId){
        Optional<User> optionalReferenceUser = userApplicationService.findById(id);
        if (optionalReferenceUser.isPresent()){
            Optional<Booking> optionalBooking = find(id, financialLedgerId, bookingId);
            if (optionalBooking.isPresent()) {
                User user = optionalReferenceUser.get();
                Booking booking = optionalBooking.get();
                if (booking.getReferencedUsers().contains(user) || user.getReferencedBookings().contains(booking)){
                    mediator.onDeleteReferenceUserToBooking(user, booking, this);
                    onDeleteReferenceUserToBooking(user, booking);
                    return true;
                }
            }
        }
        return false;
    }

}
