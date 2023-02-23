package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.financialledger.FinancialLedgerDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;

public class FinancialLedgerColleague extends Colleague {

    private final FinancialLedgerDomainService financialLedgerDomainService;

    public FinancialLedgerColleague(final ConcreteApplicationMediator mediator, final FinancialLedgerDomainService financialLedgerDomainService) {
        super(mediator);
        this.financialLedgerDomainService = financialLedgerDomainService;
    }

    @Override
    public void onUnlinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {
        financialLedger.getAuthorizedUser().remove(user);
        financialLedgerDomainService.save(financialLedger);
        if (financialLedger.getAuthorizedUser().size() == 0){
            getMediator().onDeleteFinancialLedger(financialLedger, this);
            onDeleteFinancialLedger(financialLedger);
        }
    }

    @Override
    public void onDeleteUser(User user) {
        user.getFinancialLedgers().forEach(financialLedger -> onUnlinkUserToFinancialLedger(user, financialLedger));
    }

    @Override
    public void onDeleteFinancialLedger(FinancialLedger financialLedger) {
        financialLedgerDomainService.deleteById(financialLedger.getId());
    }

    @Override
    public void onDeleteBookingCategory(BookingCategory bookingCategory) {
        FinancialLedger financialLedger = bookingCategory.getFinancialLedger();
        financialLedger.getBookingCategories().remove(bookingCategory);
        financialLedgerDomainService.save(financialLedger);
    }

    @Override
    public void onDeleteBooking(BookingAggregate bookingAggregate) {
        FinancialLedger financialLedger = bookingAggregate.getFinancialLedger();
        financialLedger.getBookingAggregates().remove(bookingAggregate);
        financialLedgerDomainService.save(financialLedger);
    }

}
