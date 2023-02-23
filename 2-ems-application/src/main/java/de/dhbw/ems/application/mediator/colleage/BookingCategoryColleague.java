package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.bookingcategory.BookingCategoryDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;


public class BookingCategoryColleague extends Colleague {

    private final BookingCategoryDomainService bookingCategoryDomainService;

    public BookingCategoryColleague(final ConcreteApplicationMediator mediator, final BookingCategoryDomainService bookingCategoryDomainService) {
        super(mediator);
        this.bookingCategoryDomainService = bookingCategoryDomainService;
    }

    @Override
    public void onDeleteBookingCategory(BookingCategory bookingCategory) {
        bookingCategoryDomainService.deleteById(bookingCategory.getId());
    }

}
