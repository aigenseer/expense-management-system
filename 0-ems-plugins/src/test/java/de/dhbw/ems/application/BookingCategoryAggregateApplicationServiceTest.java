package de.dhbw.ems.application;

import de.dhbw.ems.application.booking.aggregate.BookingAggregateApplicationService;
import de.dhbw.ems.application.bookingcategory.aggregate.BookingCategoryAggregateApplicationService;
import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryApplicationService;
import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.application.financialledger.FinancialLedgerApplicationService;
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
public class BookingCategoryAggregateApplicationServiceTest {

    @Autowired
    private BookingCategoryAggregateApplicationService bookingCategoryAggregateApplicationService;
    @Autowired
    private BookingCategoryApplicationService bookingCategoryApplicationService;
    @Autowired
    private FinancialLedgerApplicationService financialLedgerApplicationService;
    @Autowired
    private BookingAggregateApplicationService bookingAggregateApplicationService;

    private FinancialLedger financialLedger;
    private BookingCategory bookingCategory;
    private BookingAggregate bookingAggregate;

    private BookingCategoryAggregate bookingCategoryAggregate1;
    private BookingCategoryAggregate bookingCategoryAggregate2;

    private final BookingCategoryAttributeData attributeData = BookingCategoryAttributeData.builder()
            .title("NewTitle")
            .build();

    @Before
    public void setup(){
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(UUID.fromString("12345678-1234-1234-a123-123456789011"));
        assertTrue(optionalFinancialLedger.isPresent());
        financialLedger = optionalFinancialLedger.get();

        Optional<BookingCategory> optionalBookingCategory = bookingCategoryApplicationService.findById(UUID.fromString("12345678-1234-1234-a123-123456789021"));
        assertTrue(optionalBookingCategory.isPresent());
        bookingCategory = optionalBookingCategory.get();

        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryAggregateApplicationService.findById(UUID.fromString("12345678-1234-1234-a123-123456789221"));
        assertTrue(optionalBookingCategoryAggregate.isPresent());
        bookingCategoryAggregate1 = optionalBookingCategoryAggregate.get();

        bookingCategoryAggregate2 = BookingCategoryAggregate.builder()
                .id(UUID.randomUUID())
                .bookingCategoryId(bookingCategory.getId())
                .bookingCategory(bookingCategory)
                .financialLedgerId(financialLedger.getId())
                .financialLedger(financialLedger)
                .build();

    }


    @Test
    public void testSave() {
        bookingCategoryAggregateApplicationService.save(bookingCategoryAggregate2);
        Optional<BookingCategoryAggregate> result = bookingCategoryAggregateApplicationService.findById(bookingCategoryAggregate2.getId());
        assertTrue(result.isPresent());
        checkAggregate(bookingCategoryAggregate2, result.get());
    }

    @Test
    public void testDeleteById() {
        bookingCategoryAggregateApplicationService.deleteById(bookingCategoryAggregate1.getId());
        Optional<BookingCategoryAggregate> result = bookingCategoryAggregateApplicationService.findById(bookingCategoryAggregate1.getId());
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindById() {
        Optional<BookingCategoryAggregate> result = bookingCategoryAggregateApplicationService.findById(bookingCategoryAggregate1.getId());
        assertTrue(result.isPresent());
        checkAggregate(bookingCategoryAggregate1, result.get());
    }

    private void checkAggregate(BookingCategoryAggregate expectedAggregate, BookingCategoryAggregate actualAggregate) {
        assertEquals(expectedAggregate.getId(), expectedAggregate.getId());
        assertEquals(expectedAggregate.getBookingCategoryId(), expectedAggregate.getBookingCategoryId());
        assertEquals(expectedAggregate.getFinancialLedgerId(), expectedAggregate.getFinancialLedgerId());
    }

    @Test
    public void testCreateByAttributeData() {
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryAggregateApplicationService.createByAttributeData(financialLedger, attributeData);
        assertTrue(optionalBookingCategoryAggregate.isPresent());
        checkAttributeData(attributeData, optionalBookingCategoryAggregate.get());
        assertEquals(financialLedger, optionalBookingCategoryAggregate.get().getFinancialLedger());
    }

    @Test
    public void testUpdateByAttributeData() {
        Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryAggregateApplicationService.updateByAttributeData(bookingCategoryAggregate1, attributeData);
        assertTrue(optionalBookingCategoryAggregate.isPresent());
        checkAttributeData(attributeData, optionalBookingCategoryAggregate.get());
        optionalBookingCategoryAggregate = bookingCategoryAggregateApplicationService.findById(bookingCategoryAggregate1.getId());
        assertTrue(optionalBookingCategoryAggregate.isPresent());
        checkAttributeData(attributeData, optionalBookingCategoryAggregate.get());
    }

    private void checkAttributeData(BookingCategoryAttributeData attributeData, BookingCategoryAggregate actualAggregate) {
        assertEquals(attributeData.getTitle(), actualAggregate.getBookingCategory().getTitle());
    }

}
