package de.dhbw.cleanproject.application.mediator.colleage;

import de.dhbw.cleanproject.application.booking.BookingApplicationService;
import de.dhbw.cleanproject.application.mediator.ConcreteApplicationMediator;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.user.User;

public class BookingColleague implements Colleague {

    private final ConcreteApplicationMediator mediator;
    private final BookingApplicationService bookingApplicationService;

    public BookingColleague(final ConcreteApplicationMediator mediator, final BookingApplicationService bookingApplicationService) {
        this.mediator = mediator;
        this.bookingApplicationService = bookingApplicationService;
        this.mediator.addColleague(this);
    }

    @Override
    public void onLinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {

    }

    @Override
    public void onUnlinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {

    }

    @Override
    public void onCreateBooking(User user, FinancialLedger financialLedger, Booking booking) {

    }

    @Override
    public void onReferenceUserToBooking(User user, Booking booking) {
        booking.getReferencedUsers().add(user);
        bookingApplicationService.save(booking);
    }

    @Override
    public void onDeleteReferenceUserToBooking(User user, Booking booking) {
        if (booking.getReferencedUsers().contains(user)){
            booking.getReferencedUsers().remove(user);
            bookingApplicationService.save(booking);
        }
    }

    @Override
    public void onDeleteUser(User user) {
        user.getReferencedBookings().forEach(booking -> {
            mediator.onDeleteBooking(booking, this);
            onDeleteBooking(booking);
        });
    }

    @Override
    public void onDeleteFinancialLedger(FinancialLedger financialLedger) {
        financialLedger.getBookings().forEach(booking -> {
            mediator.onDeleteBooking(booking, this);
            onDeleteBooking(booking);
        });
    }

    @Override
    public void onDeleteBookingCategory(BookingCategory bookingCategory) {
        bookingCategory.getBookings().forEach(booking -> {
            booking.setCategory(null);
            bookingApplicationService.save(booking);
        });
    }

    @Override
    public void onDeleteBooking(Booking booking) {
        bookingApplicationService.deleteById(booking.getId());
    }
}
