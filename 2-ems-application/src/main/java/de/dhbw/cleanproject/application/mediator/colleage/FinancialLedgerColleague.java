package de.dhbw.cleanproject.application.mediator.colleage;

import de.dhbw.cleanproject.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.cleanproject.application.mediator.ConcreteApplicationMediator;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.user.User;

public class FinancialLedgerColleague implements Colleague {

    private final ConcreteApplicationMediator mediator;
    private final FinancialLedgerApplicationService financialLedgerApplicationService;

    public FinancialLedgerColleague(final ConcreteApplicationMediator mediator, final FinancialLedgerApplicationService financialLedgerApplicationService) {
        this.mediator = mediator;
        this.financialLedgerApplicationService = financialLedgerApplicationService;
        this.mediator.addColleague(this);
    }

    @Override
    public void onLinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {

    }

    @Override
    public void onUnlinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {
        financialLedger.getAuthorizedUser().remove(user);
        financialLedgerApplicationService.save(financialLedger);
        if (financialLedger.getAuthorizedUser().size() == 0){
            mediator.onDeleteFinancialLedger(financialLedger, this);
            onDeleteFinancialLedger(financialLedger);
        }
    }

    @Override
    public void onCreateBooking(User user, FinancialLedger financialLedger, Booking booking) {
        financialLedger.getBookings().add(booking);
        financialLedgerApplicationService.save(financialLedger);
    }

    @Override
    public void onReferenceUserToBooking(User user, Booking booking) {

    }

    @Override
    public void onDeleteReferenceUserToBooking(User user, Booking booking) {

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
