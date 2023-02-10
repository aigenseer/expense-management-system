package de.dhbw.cleanproject.application.mediator.colleage;

import de.dhbw.cleanproject.application.bookingcategory.BookingCategoryApplicationService;
import de.dhbw.cleanproject.application.mediator.ConcreteApplicationMediator;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.user.User;


public class BookingCategoryColleague implements Colleague {

    private final ConcreteApplicationMediator mediator;
    private final BookingCategoryApplicationService bookingCategoryApplicationService;

    public BookingCategoryColleague(final ConcreteApplicationMediator mediator, final BookingCategoryApplicationService bookingCategoryApplicationService) {
        this.mediator = mediator;
        this.bookingCategoryApplicationService = bookingCategoryApplicationService;
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

    }

    @Override
    public void onDeleteReferenceUserToBooking(User user, Booking booking) {

    }

    @Override
    public void onDeleteUser(User user) {

    }

    @Override
    public void onDeleteFinancialLedger(FinancialLedger financialLedger) {
        financialLedger.getBookingCategories().forEach(this::onDeleteBookingCategory);
    }

    @Override
    public void onDeleteBookingCategory(BookingCategory bookingCategory) {
        bookingCategoryApplicationService.deleteById(bookingCategory.getId());
    }

    @Override
    public void onDeleteBooking(Booking booking) {
        BookingCategory bookingCategory = booking.getCategory();
        if (bookingCategory != null){
            bookingCategory.getBookings().remove(booking);
            bookingCategoryApplicationService.save(bookingCategory);
        }
    }
}
