package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.user.UserApplicationService;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;

public class UserColleague implements Colleague {

    private final ConcreteApplicationMediator mediator;
    private final UserApplicationService userApplicationService;

    public UserColleague(final ConcreteApplicationMediator mediator, final UserApplicationService userApplicationService) {
        this.mediator = mediator;
        this.userApplicationService = userApplicationService;
        this.mediator.addColleague(this);
    }

    @Override
    public void onLinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {
        user.getFinancialLedgers().add(financialLedger);
        userApplicationService.save(user);
    }

    @Override
    public void onUnlinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {
        user.getFinancialLedgers().remove(financialLedger);
        userApplicationService.save(user);
    }

    @Override
    public void onCreateBooking(User user, FinancialLedger financialLedger, Booking booking) {
        user.getCreatedBookings().add(booking);
        userApplicationService.save(user);
    }

    @Override
    public void onReferenceUserToBooking(User user, Booking booking) {
        user.getReferencedBookings().add(booking);
        userApplicationService.save(user);
    }

    @Override
    public void onDeleteReferenceUserToBooking(User user, Booking booking) {
        if (user.getReferencedBookings().contains(booking)){
            user.getReferencedBookings().remove(booking);
            userApplicationService.save(user);
        }
    }

    @Override
    public void onDeleteUser(User user) {
        userApplicationService.deleteById(user.getId());
    }

    @Override
    public void onDeleteFinancialLedger(FinancialLedger financialLedger) {
        financialLedger.getAuthorizedUser().forEach(user -> onUnlinkUserToFinancialLedger(user, financialLedger));
    }

    @Override
    public void onDeleteBookingCategory(BookingCategory bookingCategory) {

    }

    @Override
    public void onDeleteBooking(Booking booking) {
        booking.getReferencedUsers().forEach(user -> {
            mediator.onDeleteReferenceUserToBooking(user, booking, this);
            onDeleteReferenceUserToBooking(user, booking);
        });
        User user = booking.getUser();
        user.getCreatedBookings().remove(booking);
        userApplicationService.save(user);
    }
}
