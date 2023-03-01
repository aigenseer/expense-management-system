package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.bookingcategory.aggregate.BookingCategoryAggregateDomainService;
import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;


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

    @Override
    public void onDeleteFinancialLedger(FinancialLedgerAggregate financialLedgerAggregate) {
        financialLedgerAggregate.getBookingCategoriesAggregates().forEach(bookingCategoryAggregate -> {
            getMediator().onDeleteBookingCategory(bookingCategoryAggregate, this);
            onDeleteBookingCategory(bookingCategoryAggregate);
        });
    }

}
