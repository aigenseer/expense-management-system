package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.bookingcategory.BookingCategoryApplicationService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;


public class BookingCategoryColleague extends Colleague {

    private final BookingCategoryApplicationService bookingCategoryApplicationService;

    public BookingCategoryColleague(final ConcreteApplicationMediator mediator, final BookingCategoryApplicationService bookingCategoryApplicationService) {
        super(mediator);
        this.bookingCategoryApplicationService = bookingCategoryApplicationService;
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
