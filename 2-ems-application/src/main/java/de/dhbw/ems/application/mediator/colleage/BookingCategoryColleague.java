package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.domain.service.bookingcategory.aggregate.BookingCategoryAggregateDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;


public class BookingCategoryColleague extends Colleague {

    private final BookingCategoryAggregateDomainService bookingCategoryAggregateDomainService;

    public BookingCategoryColleague(final ConcreteApplicationMediator mediator, final BookingCategoryAggregateDomainService bookingCategoryAggregateDomainService) {
        super(mediator);
        this.bookingCategoryAggregateDomainService = bookingCategoryAggregateDomainService;
    }

    @Override
    public void onDeleteBookingCategory(BookingCategoryAggregate bookingCategoryAggregate) {
        bookingCategoryAggregateDomainService.deleteById(bookingCategoryAggregate.getId());
    }

}
