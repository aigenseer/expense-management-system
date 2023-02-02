package de.dhbw.cleanproject.application;

import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserOperationService {

    private final UserApplicationService userApplicationService;
    private final FinancialLedgerApplicationService financialLedgerApplicationService;
    private final BookingApplicationService bookingApplicationService;
    private final BookingCategoryApplicationService bookingCategoryApplicationService;

    public Optional<FinancialLedger> findFinancialLedgerByUserId(UUID id, UUID financialLedgerId){
        Optional<User> userOptional = userApplicationService.findById(id);
        if (userOptional.isPresent()){
            return userOptional.get().getFinancialLedgers().stream().filter(f -> f.getId().equals(financialLedgerId)).findFirst();
        }
        return Optional.empty();
    }

    public boolean hasUserPermissionToFinancialLedger(UUID id, UUID financialLedgerId){
        return findFinancialLedgerByUserId(id, financialLedgerId).isPresent();
    }

    public boolean appendUserToFinancialLedger(UUID id, UUID financialLedgerId){
        Optional<User> userOptional = userApplicationService.findById(id);
        if (userOptional.isPresent()){
            Optional<FinancialLedger> financialLedgerOptional = financialLedgerApplicationService.findById(financialLedgerId);
            if (financialLedgerOptional.isPresent()){
                User user = userOptional.get();
                user.getFinancialLedgers().add(financialLedgerOptional.get());
                userApplicationService.save(user);
                return true;
            }
        }
        return false;
    }

    public Optional<FinancialLedger> addFinancialLedgerByUserId(UUID id, FinancialLedger financialLedger){
        Optional<User> userOptional = userApplicationService.findById(id);
        if (userOptional.isPresent()){
            financialLedger = financialLedgerApplicationService.save(financialLedger);
            User user = userOptional.get();
            user.getFinancialLedgers().add(financialLedger);
            userApplicationService.save(user);
            return findFinancialLedgerByUserId(id, financialLedger.getId());
        }
        return Optional.empty();
    }

    public Optional<Booking> getBooking(UUID id, UUID financialLedgerId, UUID bookingId){
        Optional<FinancialLedger> optionalFinancialLedger = findFinancialLedgerByUserId(id, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        return optionalFinancialLedger.get().getBookings().stream().filter(b -> b.getId().equals(bookingId)).findFirst();
    }

    public Optional<BookingCategory> getBookingCategory(UUID id, UUID financialLedgerId, UUID bookingCategoryId){
        Optional<FinancialLedger> optionalFinancialLedger = findFinancialLedgerByUserId(id, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        return optionalFinancialLedger.get().getBookingCategories().stream().filter(b -> b.getId().equals(bookingCategoryId)).findFirst();
    }

    public boolean existsBookingCategoryById(UUID id, UUID financialLedgerId, UUID bookingCategoryId){
        return getBookingCategory(id, financialLedgerId, bookingCategoryId).isPresent();
    }

    public boolean deleteBookingCategoryById(UUID id, UUID financialLedgerId, UUID bookingCategoryId){
        Optional<BookingCategory> optionalBookingCategory = getBookingCategory(id, financialLedgerId, bookingCategoryId);
        if (optionalBookingCategory.isPresent()) {
            optionalBookingCategory.get().getBookings().forEach(booking -> {
                booking.setCategory(null);
                bookingApplicationService.save(booking);
            });
            FinancialLedger financialLedger = optionalBookingCategory.get().getFinancialLedger();
            financialLedger.getBookingCategories().remove(optionalBookingCategory.get());
            financialLedgerApplicationService.save(financialLedger);
            bookingCategoryApplicationService.deleteById(bookingCategoryId);
            return true;
        }
        return false;
    }

    public Optional<Booking> addBooking(UUID id, UUID financialLedgerId, Booking booking){
        Optional<FinancialLedger> optionalFinancialLedger = findFinancialLedgerByUserId(id, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        bookingApplicationService.save(booking);
        FinancialLedger financialLedger = optionalFinancialLedger.get();
        financialLedger.getBookings().add(booking);
        financialLedgerApplicationService.save(financialLedger);
        return Optional.of(booking);
    }

    public boolean existsBookingById(UUID id, UUID financialLedgerId, UUID bookingId){
        return getBooking(id, financialLedgerId, bookingId).isPresent();
    }

    public boolean deleteBookingById(UUID id, UUID financialLedgerId, UUID bookingId){
        Optional<Booking> optionalBooking = getBooking(id, financialLedgerId, bookingId);
        if (optionalBooking.isPresent()) {
            optionalBooking.get().getReferencedUsers().forEach(user -> {
                user.getReferencedBookings().remove(optionalBooking.get());
                userApplicationService.save(user);
            });
            FinancialLedger financialLedger = optionalBooking.get().getFinancialLedger();
            financialLedger.getBookings().remove(optionalBooking.get());
            financialLedgerApplicationService.save(financialLedger);

            BookingCategory bookingCategory = optionalBooking.get().getCategory();
            bookingCategory.getBookings().remove(optionalBooking.get());
            bookingCategoryApplicationService.save(bookingCategory);

            bookingApplicationService.deleteById(bookingId);
            return true;
        }
        return false;
    }

    public Optional<BookingCategory> addBookingCategory(UUID id, UUID financialLedgerId, BookingCategory bookingCategory){
        Optional<FinancialLedger> optionalFinancialLedger = findFinancialLedgerByUserId(id, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        bookingCategoryApplicationService.save(bookingCategory);
        FinancialLedger financialLedger = optionalFinancialLedger.get();
        financialLedger.getBookingCategories().add(bookingCategory);
        financialLedgerApplicationService.save(financialLedger);
        return Optional.of(bookingCategory);
    }

    public boolean referenceUserToBooking(UUID id, UUID financialLedgerId, UUID bookingId, UUID referenceUserId){
        Optional<User> optionalReferenceUser = userApplicationService.findById(referenceUserId);
        if (optionalReferenceUser.isPresent()){
            Optional<Booking> optionalBooking = getBooking(id, financialLedgerId, bookingId);
            if (optionalBooking.isPresent()){
                User user = optionalReferenceUser.get();
                Booking booking = optionalBooking.get();

                user.getReferencedBookings().add(booking);
                userApplicationService.save(user);
                booking.getReferencedUsers().add(optionalReferenceUser.get());
                bookingApplicationService.save(booking);
                return true;
            }
        }
        return false;
    }

}
