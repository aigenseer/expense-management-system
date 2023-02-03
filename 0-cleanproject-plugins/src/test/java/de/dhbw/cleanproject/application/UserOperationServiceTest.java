package de.dhbw.cleanproject.application;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.cleanproject.abstractioncode.valueobject.money.Money;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest()
@ComponentScan("de.dhbw")
public class UserOperationServiceTest {

    @Autowired
    private UserOperationService userOperationService;
    @Autowired
    private UserApplicationService userApplicationService;
    @Autowired
    private FinancialLedgerApplicationService financialLedgerApplicationService;
    @Autowired
    private BookingCategoryApplicationService bookingCategoryApplicationService;
    @Autowired
    private BookingApplicationService bookingApplicationService;

    private final UUID userId = UUID.fromString("12345678-1234-1234-a123-123456789001");
    private final UUID userId2 = UUID.fromString("12345678-1234-1234-a123-123456789002");
    private User user;
    private final UUID financialLedgerId = UUID.fromString("12345678-1234-1234-a123-123456789011");
    private FinancialLedger financialLedger;
    private final UUID bookingCategoryId = UUID.fromString("12345678-1234-1234-a123-123456789021");
    private BookingCategory bookingCategory;
    private final UUID bookingId = UUID.fromString("12345678-1234-1234-a123-123456789031");
    private Booking booking;

    @Before
    public void setup(){
        Optional<User> optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        user = optionalUser.get();

        Optional<BookingCategory> optionalBookingCategory = bookingCategoryApplicationService.findById(bookingCategoryId);
        assertTrue(optionalBookingCategory.isPresent());
        bookingCategory = optionalBookingCategory.get();

        Optional<FinancialLedger> optionalFinancialLedger =  financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        financialLedger = optionalFinancialLedger.get();

        Optional<Booking> optionalBooking =  bookingApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        booking = optionalBooking.get();
    }


    @Test
    public void testFindFinancialLedgerByUserId() {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        assertEquals(financialLedger, optionalFinancialLedger.get());
    }

    @Test
    public void testHasUserPermissionToFinancialLedger() {
        boolean result = userOperationService.hasUserPermissionToFinancialLedger(userId, financialLedgerId);
        assertTrue(result);
    }

    @Test
    public void testAppendUserToFinancialLedger() {
        boolean result = userOperationService.appendUserToFinancialLedger(userId, financialLedgerId);
        assertTrue(result);
        Optional<User> optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        Optional<FinancialLedger> optionalFinancialLedger = optionalUser.get().getFinancialLedgers().stream().filter(f -> f.getId().equals(financialLedgerId)).findFirst();
        assertTrue(optionalFinancialLedger.isPresent());
        assertEquals(financialLedger, optionalFinancialLedger.get());
    }

    @Test
    public void testAddFinancialLedgerByUserId() {
        FinancialLedger entity = FinancialLedger.builder()
                .id(UUID.fromString("12345678-1234-1234-a123-123456789011"))
                .name("Example-Financial-Ledger-3")
                .build();
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.addFinancialLedgerByUserId(userId, entity);
        assertTrue(optionalFinancialLedger.isPresent());
        assertEquals(entity.getId(), optionalFinancialLedger.get().getId());

        Optional<User> optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        optionalFinancialLedger = optionalUser.get().getFinancialLedgers().stream().filter(f -> f.getId().equals(entity.getId())).findFirst();
        assertTrue(optionalFinancialLedger.isPresent());
        assertEquals(entity.getId(), optionalFinancialLedger.get().getId());
    }

    @Test
    public void testGetBooking() {
        Optional<Booking> optionalBooking = userOperationService.getBooking(userId, financialLedgerId, bookingId);
        assertTrue(optionalBooking.isPresent());
        assertEquals(booking, optionalBooking.get());
    }

    @Test
    public void testGetBookingCategory() {
        Optional<BookingCategory> optionalBookingCategory = userOperationService.getBookingCategory(userId, financialLedgerId, bookingCategoryId);
        assertTrue(optionalBookingCategory.isPresent());
        assertEquals(bookingCategory, optionalBookingCategory.get());
    }

    @Test
    public void testExistsBookingCategoryById() {
        boolean result = userOperationService.existsBookingCategoryById(userId, financialLedgerId, bookingCategoryId);
        assertTrue(result);
    }

    @Test
    public void testDeleteBookingCategoryById() {
        Optional<Booking> optionalBooking = bookingApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        assertEquals(booking, optionalBooking.get());
        assertEquals(bookingCategory, optionalBooking.get().getCategory());

        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        Optional<BookingCategory> optionalBookingCategory = optionalFinancialLedger.get().getBookingCategories().stream().filter(category -> category.equals(bookingCategory)).findFirst();
        assertTrue(optionalBookingCategory.isPresent());
        assertEquals(bookingCategory, optionalBookingCategory.get());

        boolean result = userOperationService.deleteBookingCategoryById(userId, financialLedgerId, bookingCategoryId);
        assertTrue(result);

        optionalBooking = bookingApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        assertEquals(null, optionalBooking.get().getCategory());

        optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        optionalBookingCategory = optionalFinancialLedger.get().getBookingCategories().stream().filter(category -> category.equals(bookingCategory)).findFirst();
        assertFalse(optionalBookingCategory.isPresent());
    }

    @Test
    public void testAddBooking() {
        Booking entity = Booking.builder()
                .id(UUID.fromString("12345678-1234-1234-a123-123456789031"))
                .title("Example-Booking-3")
                .user(user)
                .category(bookingCategory)
                .money(new Money(19.00, CurrencyType.EURO))
                .financialLedgerId(financialLedgerId)
                .creationDate(LocalDate.now())
                .referencedUsers(new HashSet<User>(){{add(user);}})
                .build();
        Optional<Booking> optionalBooking = userOperationService.addBooking(userId, financialLedgerId, booking);
        assertTrue(optionalBooking.isPresent());
        assertEquals(entity.getId(), optionalBooking.get().getId());

        Optional<User> optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        optionalBooking = optionalUser.get().getReferencedBookings().stream().filter(f -> f.getId().equals(entity.getId())).findFirst();
        assertTrue(optionalBooking.isPresent());
        assertEquals(entity.getId(), optionalBooking.get().getId());

        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        optionalBooking = optionalFinancialLedger.get().getBookings().stream().filter(f -> f.getId().equals(entity.getId())).findFirst();
        assertTrue(optionalBooking.isPresent());
    }

    @Test
    public void testExistsBookingById() {
        boolean result = userOperationService.existsBookingById(userId, financialLedgerId, bookingId);
        assertTrue(result);
    }

    @Test
    public void testDeleteBookingById() {
        boolean result = userOperationService.deleteBookingById(userId, financialLedgerId, bookingId);
        assertTrue(result);
        Optional<Booking> optionalBooking = bookingApplicationService.findById(bookingId);
        assertFalse(optionalBooking.isPresent());

        Optional<User> optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        optionalBooking = optionalUser.get().getReferencedBookings().stream().filter(f -> f.getId().equals(bookingId)).findFirst();
        assertFalse(optionalBooking.isPresent());

        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        optionalBooking = optionalFinancialLedger.get().getBookings().stream().filter(f -> f.getId().equals(bookingId)).findFirst();
        assertFalse(optionalBooking.isPresent());

        Optional<BookingCategory> optionalBookingCategory = bookingCategoryApplicationService.findById(bookingCategoryId);
        assertTrue(optionalBookingCategory.isPresent());
        optionalBooking = optionalBookingCategory.get().getBookings().stream().filter(f -> f.getId().equals(bookingId)).findFirst();
        assertFalse(optionalBooking.isPresent());
    }

    @Test
    public void testAddBookingCategory() {
        BookingCategory entity = BookingCategory.builder()
                .id(UUID.fromString("12345678-1234-1234-a123-123456789022"))
                .title("Example-Category-2")
                .financialLedger(financialLedger)
                .build();
        Optional<BookingCategory> optionalBookingCategory = userOperationService.addBookingCategory(userId, financialLedgerId, entity);
        assertTrue(optionalBookingCategory.isPresent());
        assertEquals(entity.getId(), optionalBookingCategory.get().getId());

        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        optionalBookingCategory = optionalFinancialLedger.get().getBookingCategories().stream().filter(f -> f.getId().equals(entity.getId())).findFirst();
        assertTrue(optionalBookingCategory.isPresent());
        assertEquals(entity.getId(), optionalBookingCategory.get().getId());
    }

    @Test
    public void testReferenceUserToBooking() {
        boolean result = userOperationService.referenceUserToBooking(userId, financialLedgerId, bookingId, userId2);
        assertTrue(result);

        Optional<User> optionalUser = userApplicationService.findById(userId2);
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        Optional<Booking> optionalBooking = optionalUser.get().getReferencedBookings().stream().filter(b -> b.getId().equals(bookingId)).findFirst();
        assertTrue(optionalBooking.isPresent());

        optionalBooking = bookingApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        optionalUser = optionalBooking.get().getReferencedUsers().stream().filter(u -> u.equals(user)).findFirst();
        assertTrue(optionalUser.isPresent());

        assertEquals(user, optionalUser.get());
    }

    @Test
    public void testUnlinkUserToFinancialLedger(){
        boolean result = userOperationService.unlinkUserToFinancialLedger(userId, financialLedgerId);
        assertTrue(result);

        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        Optional<User> optionalUser = optionalFinancialLedger.get().getAuthorizedUser().stream().filter(f -> f.getId().equals(userId)).findFirst();
        assertFalse(optionalUser.isPresent());

        optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        optionalFinancialLedger = optionalUser.get().getFinancialLedgers().stream().filter(f -> f.getId().equals(f)).findFirst();
        assertFalse(optionalFinancialLedger.isPresent());
    }


    @Test
    public void testDeleteFinancialLedgerById() {
        boolean result = userOperationService.deleteFinancialLedgerById(userId, financialLedgerId);
        assertTrue(result);
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertFalse(optionalFinancialLedger.isPresent());

        Optional<User> optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        optionalFinancialLedger = optionalUser.get().getFinancialLedgers().stream().filter(f -> f.getId().equals(financialLedgerId)).findFirst();
        assertFalse(optionalFinancialLedger.isPresent());

        Optional<Booking> optionalBooking = bookingApplicationService.findById(bookingId);
        assertFalse(optionalBooking.isPresent());

        Optional<BookingCategory> optionalBookingCategory = bookingCategoryApplicationService.findById(bookingCategoryId);
        assertFalse(optionalBookingCategory.isPresent());
    }

    @Test
    public void testUnlinkUserToBooking() {
        boolean result = userOperationService.unlinkUserToBooking(userId, financialLedgerId, bookingId);
        assertTrue(result);
        Optional<Booking> optionalBooking = bookingApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        Optional<User> optionalUser  =  optionalBooking.get().getReferencedUsers().stream().filter(f -> f.getId().equals(userId)).findFirst();
        assertFalse(optionalUser.isPresent());

        optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        optionalBooking =  optionalUser.get().getReferencedBookings().stream().filter(f -> f.getId().equals(bookingId)).findFirst();
        assertFalse(optionalBooking.isPresent());
    }

    @Test
    public void testDeleteUser(){
        boolean result = userOperationService.deleteUser(userId);
        assertTrue(result);
        Optional<User> optionalUser = userApplicationService.findById(userId);
        assertFalse(optionalUser.isPresent());

        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        optionalUser = optionalFinancialLedger.get().getAuthorizedUser().stream().filter(f -> f.getId().equals(userId)).findFirst();
        assertFalse(optionalUser.isPresent());

        Optional<Booking> optionalBooking = bookingApplicationService.findById(bookingId);
        assertFalse(optionalBooking.isPresent());
    }


}
