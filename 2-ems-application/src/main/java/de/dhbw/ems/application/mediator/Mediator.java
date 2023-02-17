package de.dhbw.ems.application.mediator;

import de.dhbw.ems.application.mediator.colleage.Colleague;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;


public interface Mediator {

    void onLinkUserToFinancialLedger(User user, FinancialLedger financialLedger, Colleague colleague);

    void onUnlinkUserToFinancialLedger(User user, FinancialLedger financialLedger, Colleague colleague);

    void onReferenceUserToBooking(User user, Booking booking, Colleague colleague);

    void onDeleteReferenceUserToBooking(User user, Booking booking, Colleague colleague);

    void onDeleteUser(User user, Colleague colleague);

    void onDeleteFinancialLedger(FinancialLedger financialLedger, Colleague colleague);

    void onDeleteBookingCategory(BookingCategory bookingCategory, Colleague colleague);

    void onDeleteBooking(Booking booking, Colleague colleague);

}
