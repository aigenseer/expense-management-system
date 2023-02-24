package de.dhbw.ems.application.mediator;

import de.dhbw.ems.application.booking.aggregate.BookingAggregateApplicationService;
import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryApplicationService;
import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.ems.application.mediator.service.BookingCategoryOperationService;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
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
    private BookingAggregateApplicationService bookingAggregateApplicationService;

    private final UUID userId = UUID.fromString("12345678-1234-1234-a123-123456789001");
    private final UUID financialLedgerId = UUID.fromString("12345678-1234-1234-a123-123456789011");
    private final UUID bookingCategoryId = UUID.fromString("12345678-1234-1234-a123-123456789021");
    private BookingCategory bookingCategory;
    private final UUID bookingId = UUID.fromString("12345678-1234-1234-a123-123456789031");
    private BookingAggregate bookingAggregate;

    @Before
    public void setup(){
        Optional<BookingCategory> optionalBookingCategory = bookingCategoryApplicationService.findById(bookingCategoryId);
        assertTrue(optionalBookingCategory.isPresent());
        bookingCategory = optionalBookingCategory.get();

        Optional<BookingAggregate> optionalBooking =  bookingAggregateApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        bookingAggregate = optionalBooking.get();
    }

    @Test
    public void testFind() {
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryOperationService.find(userId, financialLedgerId, bookingCategoryId);
        assertTrue(optionalBookingCategoryAggregate.isPresent());
        assertEquals(bookingCategory, optionalBookingCategoryAggregate.get());
    }

    @Test
    public void testExists() {
        boolean result = bookingCategoryOperationService.exists(userId, financialLedgerId, bookingCategoryId);
        assertTrue(result);
    }

    @Test
    public void testDelete() {
        Optional<BookingAggregate> optionalBooking = bookingAggregateApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        assertEquals(bookingAggregate, optionalBooking.get());
        assertEquals(bookingCategory, optionalBooking.get().getCategoryAggregate());

        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        Optional<BookingCategoryAggregate> optionalBookingCategory = optionalFinancialLedger.get().getBookingCategoriesAggregates().stream().filter(category -> category.equals(bookingCategory)).findFirst();
        assertTrue(optionalBookingCategory.isPresent());
        assertEquals(bookingCategory, optionalBookingCategory.get());

        boolean result = bookingCategoryOperationService.delete(userId, financialLedgerId, bookingCategoryId);
        assertTrue(result);

        optionalBooking = bookingAggregateApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        assertEquals(null, optionalBooking.get().getCategoryAggregate());

        optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        optionalBookingCategory = optionalFinancialLedger.get().getBookingCategoriesAggregates().stream().filter(category -> category.equals(bookingCategory)).findFirst();
        assertFalse(optionalBookingCategory.isPresent());
    }

    @Test
    public void testCreate() {
        BookingCategoryAttributeData attributeData = BookingCategoryAttributeData.builder().title("Example-Category-2").build();
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryOperationService.create(userId, financialLedgerId, attributeData);
        assertTrue(optionalBookingCategoryAggregate.isPresent());
        assertEquals(attributeData.getTitle(), optionalBookingCategoryAggregate.get().getBookingCategory().getTitle());

        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        Optional<BookingCategoryAggregate> optionalReferencedBookingCategoryAggregate = optionalFinancialLedger.get().getBookingCategoriesAggregates().stream().filter(f -> f.getId().equals(optionalBookingCategoryAggregate.get().getId())).findFirst();
        assertTrue(optionalReferencedBookingCategoryAggregate.isPresent());
        assertEquals(optionalReferencedBookingCategoryAggregate.get().getId(), optionalReferencedBookingCategoryAggregate.get().getId());
    }


}
