package de.dhbw.ems.application.mediator;

import de.dhbw.ems.application.booking.aggregate.BookingAggregateApplicationService;
import de.dhbw.ems.application.bookingcategory.aggregate.BookingCategoryAggregateApplicationService;
import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.application.financialledger.aggregate.FinancialLedgerAggregateApplicationService;
import de.dhbw.ems.application.mediator.service.BookingCategoryOperationService;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
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
    private FinancialLedgerAggregateApplicationService financialLedgerAggregateApplicationService;
    @Autowired
    private BookingCategoryAggregateApplicationService bookingCategoryAggregateApplicationService;
    @Autowired
    private BookingAggregateApplicationService bookingAggregateApplicationService;

    private final UUID userId = UUID.fromString("12345678-1234-1234-a123-123456789001");
    private final UUID financialLedgerAggregateId = UUID.fromString("12345678-1234-1234-a123-123456789111");
    private final UUID bookingCategoryAggregateId = UUID.fromString("12345678-1234-1234-a123-123456789221");
    private BookingCategoryAggregate bookingCategoryAggregate;
    private final UUID bookingId = UUID.fromString("12345678-1234-1234-a123-123456789331");
    private BookingAggregate bookingAggregate;

    @Before
    public void setup(){
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryAggregateApplicationService.findById(bookingCategoryAggregateId);
        assertTrue(optionalBookingCategoryAggregate.isPresent());
        bookingCategoryAggregate = optionalBookingCategoryAggregate.get();

        Optional<BookingAggregate> optionalBookingAggregate =  bookingAggregateApplicationService.findById(bookingId);
        assertTrue(optionalBookingAggregate.isPresent());
        bookingAggregate = optionalBookingAggregate.get();
    }

    @Test
    public void testFind() {
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryOperationService.find(userId, financialLedgerAggregateId, bookingCategoryAggregateId);
        assertTrue(optionalBookingCategoryAggregate.isPresent());
        assertEquals(bookingCategoryAggregate, optionalBookingCategoryAggregate.get());
    }

    @Test
    public void testExists() {
        boolean result = bookingCategoryOperationService.exists(userId, financialLedgerAggregateId, bookingCategoryAggregateId);
        assertTrue(result);
    }

    @Test
    public void testDelete() {
        Optional<BookingAggregate> optionalBooking = bookingAggregateApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        assertEquals(bookingAggregate, optionalBooking.get());
        assertEquals(bookingCategoryAggregate, optionalBooking.get().getCategoryAggregate());

        Optional<FinancialLedgerAggregate> optionalFinancialLedger = financialLedgerAggregateApplicationService.findById(financialLedgerAggregateId);
        assertTrue(optionalFinancialLedger.isPresent());
        Optional<BookingCategoryAggregate> optionalBookingCategory = optionalFinancialLedger.get().getBookingCategoriesAggregates().stream().filter(category -> category.equals(bookingCategoryAggregate)).findFirst();
        assertTrue(optionalBookingCategory.isPresent());
        assertEquals(bookingCategoryAggregate, optionalBookingCategory.get());

        boolean result = bookingCategoryOperationService.delete(userId, financialLedgerAggregateId, bookingCategoryAggregateId);
        assertTrue(result);

        optionalBooking = bookingAggregateApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        assertNull(optionalBooking.get().getCategoryAggregate());

        optionalFinancialLedger = financialLedgerAggregateApplicationService.findById(financialLedgerAggregateId);
        assertTrue(optionalFinancialLedger.isPresent());
        optionalBookingCategory = optionalFinancialLedger.get().getBookingCategoriesAggregates().stream().filter(category -> category.equals(bookingCategoryAggregate)).findFirst();
        assertFalse(optionalBookingCategory.isPresent());
    }

    @Test
    public void testCreate() {
        BookingCategoryAttributeData attributeData = BookingCategoryAttributeData.builder().title("Example-Category-2").build();
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryOperationService.create(userId, financialLedgerAggregateId, attributeData);
        assertTrue(optionalBookingCategoryAggregate.isPresent());
        assertEquals(attributeData.getTitle(), optionalBookingCategoryAggregate.get().getBookingCategory().getTitle());

        Optional<FinancialLedgerAggregate> optionalFinancialLedger = financialLedgerAggregateApplicationService.findById(financialLedgerAggregateId);
        assertTrue(optionalFinancialLedger.isPresent());
        Optional<BookingCategoryAggregate> optionalReferencedBookingCategoryAggregate = optionalFinancialLedger.get().getBookingCategoriesAggregates().stream().filter(f -> f.getId().equals(optionalBookingCategoryAggregate.get().getId())).findFirst();
        assertTrue(optionalReferencedBookingCategoryAggregate.isPresent());
        assertEquals(optionalReferencedBookingCategoryAggregate.get().getId(), optionalReferencedBookingCategoryAggregate.get().getId());
    }


}
