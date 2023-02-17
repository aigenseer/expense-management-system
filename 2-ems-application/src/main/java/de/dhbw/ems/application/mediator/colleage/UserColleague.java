package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.user.UserDomainService;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;

public class UserColleague extends Colleague {

    private final UserDomainService userDomainService;

    public UserColleague(final ConcreteApplicationMediator mediator, final UserDomainService userDomainService) {
        super(mediator);
        this.userDomainService = userDomainService;
    }

    @Override
    public void onLinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {
        user.getFinancialLedgers().add(financialLedger);
        userDomainService.save(user);
    }

    @Override
    public void onUnlinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {
        user.getFinancialLedgers().remove(financialLedger);
        userDomainService.save(user);
    }

    @Override
    public void onDeleteUser(User user) {
        userDomainService.deleteById(user.getId());
    }

    @Override
    public void onDeleteFinancialLedger(FinancialLedger financialLedger) {
        financialLedger.getAuthorizedUser().forEach(user -> onUnlinkUserToFinancialLedger(user, financialLedger));
    }

    @Override
    public void onDeleteBooking(Booking booking) {
        booking.getReferencedUsers().forEach(user -> {
            getMediator().onDeleteReferenceUserToBooking(user, booking, this);
            onDeleteReferenceUserToBooking(user, booking);
        });
    }
}
