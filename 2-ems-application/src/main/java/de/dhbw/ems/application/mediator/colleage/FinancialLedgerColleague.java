package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;

public class FinancialLedgerColleague extends Colleague {

    private final FinancialLedgerApplicationService financialLedgerApplicationService;

    public FinancialLedgerColleague(final ConcreteApplicationMediator mediator, final FinancialLedgerApplicationService financialLedgerApplicationService) {
        super(mediator);
        this.financialLedgerApplicationService = financialLedgerApplicationService;
    }

    @Override
    public void onUnlinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {
        financialLedger.getAuthorizedUser().remove(user);
        financialLedgerApplicationService.save(financialLedger);
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
        financialLedgerApplicationService.deleteById(financialLedger.getId());
    }

    @Override
    public void onDeleteBookingCategory(BookingCategory bookingCategory) {
        FinancialLedger financialLedger = bookingCategory.getFinancialLedger();
        financialLedger.getBookingCategories().remove(bookingCategory);
        financialLedgerApplicationService.save(financialLedger);
    }

    @Override
    public void onDeleteBooking(Booking booking) {
        FinancialLedger financialLedger = booking.getFinancialLedger();
        financialLedger.getBookings().remove(booking);
        financialLedgerApplicationService.save(financialLedger);
    }

}
