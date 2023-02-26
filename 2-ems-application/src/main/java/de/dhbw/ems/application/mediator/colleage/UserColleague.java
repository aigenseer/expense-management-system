package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.user.UserDomainService;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.ems.domain.user.User;

public class UserColleague extends Colleague {

    private final UserDomainService userDomainService;

    public UserColleague(final ConcreteApplicationMediator mediator, final UserDomainService userDomainService) {
        super(mediator);
        this.userDomainService = userDomainService;
    }

    @Override
    public void onDeleteUser(User user) {
        userDomainService.deleteById(user.getId());
    }

    @Override
    public void onDeleteFinancialLedger(FinancialLedgerAggregate financialLedgerAggregate) {
        financialLedgerAggregate.getAuthorizedUser().forEach(user -> onUnlinkUserToFinancialLedger(user, financialLedgerAggregate));
    }

    @Override
    public void onDeleteBooking(BookingAggregate bookingAggregate) {
        bookingAggregate.getReferencedUsers().forEach(user -> {
            getMediator().onDeleteReferenceUserToBooking(user, bookingAggregate, this);
            onDeleteReferenceUserToBooking(user, bookingAggregate);
        });
    }
}
