package de.dhbw.ems.application.mediator;

import de.dhbw.ems.application.mediator.colleage.Colleague;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;


public interface Mediator {

    void onLinkUserToFinancialLedger(User user, FinancialLedger financialLedger, Colleague colleague);

    void onUnlinkUserToFinancialLedger(User user, FinancialLedger financialLedger, Colleague colleague);

    void onReferenceUserToBooking(User user, BookingAggregate bookingAggregate, Colleague colleague);

    void onDeleteReferenceUserToBooking(User user, BookingAggregate bookingAggregate, Colleague colleague);

    void onDeleteUser(User user, Colleague colleague);

    void onDeleteFinancialLedger(FinancialLedger financialLedger, Colleague colleague);

    void onDeleteBookingCategory(BookingCategory bookingCategory, Colleague colleague);

    void onDeleteBooking(BookingAggregate bookingAggregate, Colleague colleague);

}
