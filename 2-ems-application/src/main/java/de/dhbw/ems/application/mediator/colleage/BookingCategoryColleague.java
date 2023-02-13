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

    public void onDeleteBookingCategory(BookingCategory bookingCategory) {
        bookingCategoryApplicationService.deleteById(bookingCategory.getId());
    }

}
