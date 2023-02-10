package de.dhbw.cleanproject.application.mediator.colleage;


import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.user.User;

public interface Colleague {

    void onLinkUserToFinancialLedger(User user, FinancialLedger financialLedger);

    void onUnlinkUserToFinancialLedger(User user, FinancialLedger financialLedger);

    void onCreateBooking(User user, FinancialLedger financialLedger, Booking booking);

    void onReferenceUserToBooking(User user, Booking booking);

    void onDeleteReferenceUserToBooking(User user, Booking booking);

    void onDeleteUser(User user);

    void onDeleteFinancialLedger(FinancialLedger financialLedger);

    void onDeleteBookingCategory(BookingCategory bookingCategory);

    void onDeleteBooking(Booking booking);

}
