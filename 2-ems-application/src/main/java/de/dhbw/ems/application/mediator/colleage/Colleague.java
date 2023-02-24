package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;

public abstract class Colleague {

    @Getter(AccessLevel.PROTECTED)
    private final ConcreteApplicationMediator mediator;

    protected Colleague(final ConcreteApplicationMediator mediator) {
        this.mediator = mediator;
        this.mediator.addColleague(this);
    }

    public void onLinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {

    }

    public void onUnlinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {

    }

    public void onReferenceUserToBooking(User user, BookingAggregate bookingAggregate) {

    }

    public void onDeleteReferenceUserToBooking(User user, BookingAggregate bookingAggregate) {

    }

    public void onDeleteUser(User user) {

    }

    public void onDeleteFinancialLedger(FinancialLedger financialLedger) {

    }

    public void onDeleteBookingCategory(BookingCategoryAggregate bookingCategoryAggregate) {

    }

    public void onDeleteBooking(BookingAggregate bookingAggregate) {

    }
}
