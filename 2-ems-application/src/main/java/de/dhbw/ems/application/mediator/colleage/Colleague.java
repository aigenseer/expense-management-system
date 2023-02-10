package de.dhbw.ems.application.mediator.colleage;


import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;

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
