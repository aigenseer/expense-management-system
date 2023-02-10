package de.dhbw.cleanproject.application.mediator;

import de.dhbw.cleanproject.application.booking.BookingApplicationService;
import de.dhbw.cleanproject.application.bookingcategory.BookingCategoryApplicationService;
import de.dhbw.cleanproject.application.bookingcategory.BookingCategoryAttributeData;
import de.dhbw.cleanproject.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.cleanproject.application.mediator.service.BookingCategoryOperationService;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
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


@RunWith(SpringRunner.class)
@DataJpaTest()
@ComponentScan("de.dhbw")
public class BookingCategoryOperationServiceTest {

    @Autowired
    private BookingCategoryOperationService bookingCategoryOperationService;
    @Autowired
    private FinancialLedgerApplicationService financialLedgerApplicationService;
    @Autowired
    private BookingCategoryApplicationService bookingCategoryApplicationService;
    @Autowired
    private BookingApplicationService bookingApplicationService;

    private final UUID userId = UUID.fromString("12345678-1234-1234-a123-123456789001");
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
        Optional<BookingCategory> optionalBookingCategory = bookingCategoryOperationService.find(userId, financialLedgerId, bookingCategoryId);
        assertTrue(optionalBookingCategory.isPresent());
        assertEquals(bookingCategory, optionalBookingCategory.get());
    }

    @Test
    public void testExists() {
        boolean result = bookingCategoryOperationService.exists(userId, financialLedgerId, bookingCategoryId);
        assertTrue(result);
    }

    @Test
    public void testDelete() {
        Optional<Booking> optionalBooking = bookingApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        assertEquals(booking, optionalBooking.get());
        assertEquals(bookingCategory, optionalBooking.get().getCategory());

        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        Optional<BookingCategory> optionalBookingCategory = optionalFinancialLedger.get().getBookingCategories().stream().filter(category -> category.equals(bookingCategory)).findFirst();
        assertTrue(optionalBookingCategory.isPresent());
        assertEquals(bookingCategory, optionalBookingCategory.get());

        boolean result = bookingCategoryOperationService.delete(userId, financialLedgerId, bookingCategoryId);
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
    public void testCreate() {
        BookingCategoryAttributeData attributeData = BookingCategoryAttributeData.builder().title("Example-Category-2").build();
        Optional<BookingCategory> optionalBookingCategory = bookingCategoryOperationService.create(userId, financialLedgerId, attributeData);
        assertTrue(optionalBookingCategory.isPresent());
        assertEquals(attributeData.getTitle(), optionalBookingCategory.get().getTitle());

        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        Optional<BookingCategory> optionalReferencedBookingCategory = optionalFinancialLedger.get().getBookingCategories().stream().filter(f -> f.getId().equals(optionalBookingCategory.get().getId())).findFirst();
        assertTrue(optionalBookingCategory.isPresent());
        assertEquals(optionalBookingCategory.get().getId(), optionalReferencedBookingCategory.get().getId());
    }


}
