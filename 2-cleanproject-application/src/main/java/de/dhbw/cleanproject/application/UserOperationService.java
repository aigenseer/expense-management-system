package de.dhbw.cleanproject.application;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.cleanproject.abstractioncode.valueobject.money.Money;
import de.dhbw.cleanproject.application.booking.BookingApplicationService;
import de.dhbw.cleanproject.application.booking.BookingAttributeData;
import de.dhbw.cleanproject.application.bookingcategory.BookingCategoryApplicationService;
import de.dhbw.cleanproject.application.bookingcategory.BookingCategoryAttributeData;
import de.dhbw.cleanproject.application.currency.exchange.CurrencyExchangeOffice;
import de.dhbw.cleanproject.application.currency.exchange.CurrencyExchangeRequest;
import de.dhbw.cleanproject.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.cleanproject.application.financialledger.FinancialLedgerAttributeData;
import de.dhbw.cleanproject.application.user.UserApplicationService;
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
    private final CurrencyExchangeOffice currencyExchangeOffice;

    public boolean deleteUser(UUID id){
        Optional<User> optionalUser = userApplicationService.findById(id);
        if (optionalUser.isPresent()){
            optionalUser.get().getReferencedBookings().forEach(booking -> {
                unlinkUserToBooking(id, booking.getFinancialLedgerId(), booking.getId());
            });
            optionalUser.get().getCreatedBookings().forEach(booking -> {
                deleteBookingById(id, booking.getFinancialLedgerId(), booking.getId());
            });
            optionalUser.get().getFinancialLedgers().forEach(financialLedger -> {
                unlinkUserToFinancialLedger(id, financialLedger.getId());
            });
            userApplicationService.deleteById(id);
            return true;
        }
        return false;
    }

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

    public boolean unlinkUserToFinancialLedger(UUID id, UUID financialLedgerId){
        Optional<User> optionalUser = userApplicationService.findById(id);
        if (optionalUser.isPresent()){
            Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
            if (optionalFinancialLedger.isPresent()){
                User user = optionalUser.get();
                FinancialLedger financialLedger = optionalFinancialLedger.get();
                if (financialLedger.getAuthorizedUser().contains(user) ||
                        user.getFinancialLedgers().contains(financialLedger)
                ){
                    user.getFinancialLedgers().remove(optionalFinancialLedger.get());
                    userApplicationService.save(user);
                    financialLedger.getAuthorizedUser().remove(user);
                    financialLedgerApplicationService.save(financialLedger);
                    return true;
                }
            }
        }
        return false;
    }

    public Optional<FinancialLedger> createFinancialLedgerByUserId(UUID id, FinancialLedgerAttributeData financialLedgerAttributeData){
        Optional<User> userOptional = userApplicationService.findById(id);
        if (userOptional.isPresent()){
            Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.createByAttributeData(financialLedgerAttributeData);
            if (optionalFinancialLedger.isPresent()){
                User user = userOptional.get();
                user.getFinancialLedgers().add(optionalFinancialLedger.get());
                userApplicationService.save(user);
                return findFinancialLedgerByUserId(id, optionalFinancialLedger.get().getId());
            }
        }
        return Optional.empty();
    }

    public boolean deleteFinancialLedgerById(UUID id, UUID financialLedgerId){
        Optional<FinancialLedger> optionalFinancialLedger = findFinancialLedgerByUserId(id, financialLedgerId);
        if (optionalFinancialLedger.isPresent()) {
            optionalFinancialLedger.get().getBookings().forEach(booking -> {
                deleteBookingById(id, financialLedgerId, booking.getId());
            });
            optionalFinancialLedger.get().getBookingCategories().forEach(category -> {
                deleteBookingCategoryById(id, financialLedgerId, category.getId());
            });
            optionalFinancialLedger.get().getAuthorizedUser().forEach(user -> {
                unlinkUserToFinancialLedger(user.getId(), financialLedgerId);
            });
            financialLedgerApplicationService.deleteById(financialLedgerId);
            return true;
        }
        return false;
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

    public Optional<Booking> addBooking(UUID id, UUID financialLedgerId, BookingAttributeData attributeData){
        Optional<User> optionalUser = userApplicationService.findById(id);
        if (!optionalUser.isPresent()) return Optional.empty();
        Optional<FinancialLedger> optionalFinancialLedger = findFinancialLedgerByUserId(id, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        Optional<Booking> optionalBooking = bookingApplicationService.createByAttributeData(optionalUser.get(), optionalFinancialLedger.get(), attributeData);
        if (!optionalBooking.isPresent()) return Optional.empty();
        User user = optionalUser.get();
        user.getCreatedBookings().add(optionalBooking.get());
        userApplicationService.save(user);

        FinancialLedger financialLedger = optionalFinancialLedger.get();
        financialLedger.getBookings().add(optionalBooking.get());
        financialLedgerApplicationService.save(financialLedger);
        return getBooking(id, financialLedgerId, optionalBooking.get().getId());
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
            User user = optionalBooking.get().getUser();
            user.getCreatedBookings().remove(optionalBooking.get());
            userApplicationService.save(user);

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

    public Optional<BookingCategory> createBookingCategory(UUID id, UUID financialLedgerId, BookingCategoryAttributeData attributeData){
        Optional<FinancialLedger> optionalFinancialLedger = findFinancialLedgerByUserId(id, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        Optional<BookingCategory> optionalBookingCategory = bookingCategoryApplicationService.createByAttributeData(optionalFinancialLedger.get(), attributeData);
        if (optionalBookingCategory.isPresent()){
            FinancialLedger financialLedger = optionalFinancialLedger.get();
            financialLedger.getBookingCategories().add(optionalBookingCategory.get());
            financialLedgerApplicationService.save(financialLedger);
            return getBookingCategory(id, financialLedgerId, optionalBookingCategory.get().getId());
        }
        return Optional.empty();

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

    public boolean unlinkUserToBooking(UUID id, UUID financialLedgerId, UUID bookingId){
        Optional<User> optionalReferenceUser = userApplicationService.findById(id);
        if (optionalReferenceUser.isPresent()){
            Optional<Booking> optionalBooking = getBooking(id, financialLedgerId, bookingId);
            if (optionalBooking.isPresent()) {
                User user = optionalReferenceUser.get();
                Booking booking = optionalBooking.get();
                if (booking.getReferencedUsers().contains(user) ||
                    user.getReferencedBookings().contains(booking)
                ){
                    user.getReferencedBookings().remove(booking);
                    userApplicationService.save(user);
                    booking.getReferencedUsers().remove(optionalReferenceUser.get());
                    bookingApplicationService.save(booking);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean exchangeCurrencyOfBooking(UUID id, UUID financialLedgerId, UUID bookingId, CurrencyType targetCurrencyType){
        Optional<Booking> optionalBooking = getBooking(id, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent() || optionalBooking.get().getMoney().getCurrencyType().equals(targetCurrencyType)) return false;
        CurrencyExchangeRequest currencyExchangeRequest = CurrencyExchangeRequest.builder().sourceCurrencyType(optionalBooking.get().getMoney().getCurrencyType()).targetCurrencyType(targetCurrencyType).build();

        Optional<Double> rate = currencyExchangeOffice.getExchangeRate(currencyExchangeRequest);
        if (!rate.isPresent()) return false;

        Booking booking = optionalBooking.get();
        Money money = booking.getMoney();
        double amount = money.getAmount() * rate.get();
        amount = Math.round(amount*100.0)/100.0;
        booking.setMoney(new Money(amount, targetCurrencyType));
        bookingApplicationService.save(booking);
        return true;
    }

}
