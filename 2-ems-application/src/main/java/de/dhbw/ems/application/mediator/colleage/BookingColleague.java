package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.booking.BookingApplicationService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.ems.domain.user.User;

public class BookingColleague extends Colleague {

    private final BookingApplicationService bookingApplicationService;

    public BookingColleague(final ConcreteApplicationMediator mediator, final BookingApplicationService bookingApplicationService) {
        super(mediator);
        this.bookingApplicationService = bookingApplicationService;
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
            getMediator().onDeleteReferenceUserToBooking(user, booking, this);
            onDeleteReferenceUserToBooking(user, booking);
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
