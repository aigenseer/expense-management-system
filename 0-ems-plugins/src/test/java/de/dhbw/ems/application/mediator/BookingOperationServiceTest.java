package de.dhbw.ems.application.mediator;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.cleanproject.abstractioncode.valueobject.money.Money;
import de.dhbw.ems.application.booking.BookingApplicationService;
import de.dhbw.ems.application.booking.BookingAttributeData;
import de.dhbw.ems.application.bookingcategory.BookingCategoryApplicationService;
import de.dhbw.ems.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.ems.application.mediator.service.BookingOperationService;
import de.dhbw.ems.application.user.UserApplicationService;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


@RunWith(SpringRunner.class)
@DataJpaTest()
@ComponentScan("de.dhbw")
public class BookingOperationServiceTest {

    @Autowired
    private BookingOperationService bookingOperationService;
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
    private final UUID financialLedgerId = UUID.fromString("12345678-1234-1234-a123-123456789011");
    private final UUID bookingCategoryId = UUID.fromString("12345678-1234-1234-a123-123456789021");
    private BookingCategory bookingCategory;
    private final UUID bookingId = UUID.fromString("12345678-1234-1234-a123-123456789031");
    private Booking booking;

    @Before
    public void setup(){
        Optional<BookingCategory> optionalBookingCategory = bookingCategoryApplicationService.findById(bookingCategoryId);
        assertTrue(optionalBookingCategory.isPresent());
        bookingCategory = optionalBookingCategory.get();

        Optional<Booking> optionalBooking =  bookingApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        booking = optionalBooking.get();
    }

    @Test
    public void testFind() {
        Optional<Booking> optionalBooking = bookingOperationService.find(userId, financialLedgerId, bookingId);
        assertTrue(optionalBooking.isPresent());
        assertEquals(booking, optionalBooking.get());
    }

    @Test
    public void testAdd() {
        BookingAttributeData attributeData = BookingAttributeData.builder()
                .title("Example-Booking-3")
                .bookingCategory(bookingCategory)
                .money(new Money(19.00, CurrencyType.EURO))
                .build();

        Optional<Booking> optionalBooking = bookingOperationService.create(userId, financialLedgerId, attributeData);
        assertTrue(optionalBooking.isPresent());
        assertEquals(attributeData.getTitle(), optionalBooking.get().getTitle());

        Optional<User> optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        Optional<Booking> optionalReferencedBooking = optionalUser.get().getCreatedBookings().stream().filter(f -> f.getId().equals(optionalBooking.get().getId())).findFirst();
        assertTrue(optionalReferencedBooking.isPresent());
        assertEquals(optionalBooking.get().getId(), optionalReferencedBooking.get().getId());

        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        optionalReferencedBooking =  optionalFinancialLedger.get().getBookings().stream().filter(f -> f.getId().equals(optionalBooking.get().getId())).findFirst();
        assertTrue(optionalReferencedBooking.isPresent());
        assertEquals(optionalBooking.get().getId(), optionalReferencedBooking.get().getId());
    }

    @Test
    public void testExist() {
        boolean result = bookingOperationService.exists(userId, financialLedgerId, bookingId);
        assertTrue(result);
    }

    @Test
    public void testDelete() {
        boolean result = bookingOperationService.delete(userId, financialLedgerId, bookingId);
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
    public void testReferenceUser() {
        boolean result = bookingOperationService.referenceUser(userId, financialLedgerId, bookingId, userId2);
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
    public void testUnlinkUserToBooking() {
        boolean result = bookingOperationService.deleteUserReference(userId, financialLedgerId, bookingId);
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

}
