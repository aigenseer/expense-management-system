package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.bookingcategory.aggregate.BookingCategoryAggregateDomainService;
import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;


public class BookingCategoryColleague extends Colleague {

    private final BookingCategoryAggregateDomainService bookingCategoryAggregateDomainService;
    private final BookingCategoryDomainService bookingCategoryDomainService;

    public BookingCategoryColleague(final ConcreteApplicationMediator mediator, final BookingCategoryAggregateDomainService bookingCategoryAggregateDomainService, final BookingCategoryDomainService bookingCategoryDomainService) {
        super(mediator);
        this.bookingCategoryAggregateDomainService = bookingCategoryAggregateDomainService;
        this.bookingCategoryDomainService = bookingCategoryDomainService;
    }

    @Override
    public void onDeleteBookingCategory(BookingCategoryAggregate bookingCategoryAggregate) {
        bookingCategoryAggregateDomainService.deleteById(bookingCategoryAggregate.getId());
        bookingCategoryDomainService.deleteById(bookingCategoryAggregate.getBookingCategoryId());
    }

}
