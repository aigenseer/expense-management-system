package de.dhbw.cleanproject.application.mediator;

import de.dhbw.cleanproject.application.mediator.colleage.Colleague;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.user.User;


public interface Mediator {

    void onLinkUserToFinancialLedger(User user, FinancialLedger financialLedger, Colleague colleague);

    void onUnlinkUserToFinancialLedger(User user, FinancialLedger financialLedger, Colleague colleague);

    void onCreateBooking(User user, FinancialLedger financialLedger, Booking booking, Colleague colleague);

    void onReferenceUserToBooking(User user, Booking booking, Colleague colleague);

    void onDeleteReferenceUserToBooking(User user, Booking booking, Colleague colleague);

    void onDeleteUser(User user, Colleague colleague);

    void onDeleteFinancialLedger(FinancialLedger financialLedger, Colleague colleague);

    void onDeleteBookingCategory(BookingCategory bookingCategory, Colleague colleague);

    void onDeleteBooking(Booking booking, Colleague colleague);

}
