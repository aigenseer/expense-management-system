package de.dhbw.ems.application.mediator;

import de.dhbw.ems.application.mediator.colleage.Colleague;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConcreteApplicationMediator implements Mediator {

    private final List<Colleague> colleagues = new ArrayList<>();

    public void addColleague(Colleague colleague) {
        colleagues.add(colleague);
    }

    @Override
    public void onLinkUserToFinancialLedger(User user, FinancialLedger financialLedger, Colleague colleague) {
        for (Colleague item : colleagues) {
            if (!item.equals(colleague)) item.onLinkUserToFinancialLedger(user, financialLedger);
        }
    }

    @Override
    public void onUnlinkUserToFinancialLedger(User user, FinancialLedger financialLedger, Colleague colleague) {
        for (Colleague item : colleagues) {
            if (!item.equals(colleague)) item.onUnlinkUserToFinancialLedger(user, financialLedger);
        }
    }

    @Override
    public void onReferenceUserToBooking(User user, BookingAggregate bookingAggregate, Colleague colleague) {
        for (Colleague item : colleagues) {
            if (!item.equals(colleague)) item.onReferenceUserToBooking(user, bookingAggregate);
        }
    }

    @Override
    public void onDeleteReferenceUserToBooking(User user, BookingAggregate bookingAggregate, Colleague colleague) {
        for (Colleague item : colleagues) {
            if (!item.equals(colleague)) item.onDeleteReferenceUserToBooking(user, bookingAggregate);
        }
    }

    @Override
    public void onDeleteUser(User user, Colleague colleague) {
        for (Colleague item : colleagues) {
            if (!item.equals(colleague)) item.onDeleteUser(user);
        }
    }

    @Override
    public void onDeleteBookingCategory(BookingCategoryAggregate bookingCategoryAggregate, Colleague colleague) {
        for (Colleague item : colleagues) {
            if (!item.equals(colleague)) item.onDeleteBookingCategory(bookingCategoryAggregate);
        }
    }

}
