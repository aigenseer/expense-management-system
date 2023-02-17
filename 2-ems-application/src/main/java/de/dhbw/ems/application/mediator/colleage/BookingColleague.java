package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.booking.BookingDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.ems.domain.user.User;

public class BookingColleague extends Colleague {

    private final BookingDomainService bookingDomainService;

    public BookingColleague(final ConcreteApplicationMediator mediator, final BookingDomainService bookingDomainService) {
        super(mediator);
        this.bookingDomainService = bookingDomainService;
    }

    @Override
    public void onReferenceUserToBooking(User user, Booking booking) {
        booking.getReferencedUsers().add(user);
        bookingDomainService.save(booking);
    }

    @Override
    public void onDeleteReferenceUserToBooking(User user, Booking booking) {
        if (booking.getReferencedUsers().contains(user)){
            booking.getReferencedUsers().remove(user);
            bookingDomainService.save(booking);
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
            bookingDomainService.save(booking);
        });
    }

    @Override
    public void onDeleteBooking(Booking booking) {
        bookingDomainService.deleteById(booking.getId());
    }

}
