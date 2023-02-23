package de.dhbw.ems.application;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.abstractioncode.valueobject.money.Money;
import de.dhbw.ems.application.booking.aggregate.BookingAggregateApplicationService;
import de.dhbw.ems.application.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.application.booking.entity.BookingApplicationService;
import de.dhbw.ems.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.booking.entity.Booking;
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
public class BookingAggregateApplicationServiceTest {

    @Autowired
    private BookingAggregateApplicationService bookingAggregateApplicationService;
    @Autowired
    private BookingApplicationService bookingApplicationService;
    @Autowired
    private FinancialLedgerApplicationService financialLedgerApplicationService;

    private FinancialLedger financialLedger;
    private Booking booking;
    private BookingAggregate bookingAggregate;

    private final BookingAggregateAttributeData attributeAggregateData = BookingAggregateAttributeData.builder()
            .title("NewTitle")
            .money(new Money(19.99, CurrencyType.EURO))
            .bookingCategory(null)
            .build();

    @Before
    public void setup(){
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(UUID.fromString("12345678-1234-1234-a123-123456789011"));
        assertTrue(optionalFinancialLedger.isPresent());
        financialLedger = optionalFinancialLedger.get();

        Optional<Booking> optionalBooking = bookingApplicationService.findById(UUID.fromString("12345678-1234-1234-a123-123456789031"));
        assertTrue(optionalBooking.isPresent());
        booking = optionalBooking.get();

        Optional<BookingAggregate> optionalBookingAggregate = bookingAggregateApplicationService.findById(UUID.fromString("12345678-1234-1234-a123-123456789331"));
        assertTrue(optionalBookingAggregate.isPresent());
        bookingAggregate = optionalBookingAggregate.get();
    }

    @Test
    public void testSave() {
        final BookingAggregate aggregate2 = BookingAggregate.builder()
                .id(UUID.fromString("12345678-1234-1234-a123-123456789332"))
                .booking(booking)
                .bookingId(booking.getId())
                .financialLedger(financialLedger)
                .financialLedgerId(financialLedger.getId())
                .build();

        bookingAggregateApplicationService.save(aggregate2);
        Optional<BookingAggregate> result = bookingAggregateApplicationService.findById(aggregate2.getId());
        assertTrue(result.isPresent());
        checkAggregate(aggregate2, result.get());
    }

    @Test
    public void testDeleteById() {
        bookingAggregateApplicationService.deleteById(bookingAggregate.getId());
        Optional<BookingAggregate> result = bookingAggregateApplicationService.findById(bookingAggregate.getId());
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindById(){
        Optional<BookingAggregate> result = bookingAggregateApplicationService.findById(bookingAggregate.getId());
        assertTrue(result.isPresent());
        checkAggregate(bookingAggregate, result.get());
    }

    private void checkAggregate(BookingAggregate expectedAggregate, BookingAggregate actualAggregate){
        assertEquals(expectedAggregate.getId(), actualAggregate.getId());
        assertEquals(expectedAggregate.getBooking().getTitle(), actualAggregate.getBooking().getTitle());
    }

    @Test
    public void testCreateByAttributeData() {
        Optional<BookingAggregate> optionalBookingAggregate = bookingAggregateApplicationService.createByAttributeData(bookingAggregate.getCreator(), bookingAggregate.getFinancialLedger(), attributeAggregateData);
        assertTrue(optionalBookingAggregate.isPresent());
        assertEquals(bookingAggregate.getCreator(), optionalBookingAggregate.get().getCreator());
        assertEquals(bookingAggregate.getFinancialLedger().getId(), optionalBookingAggregate.get().getFinancialLedgerId());
    }

    @Test
    public void testUpdateByAttributeData() {
        attributeAggregateData.setBookingCategory(bookingAggregate.getCategory());
        Optional<BookingAggregate> optionalBookingAggregate = bookingAggregateApplicationService.updateByAttributeData(bookingAggregate, attributeAggregateData);
        assertTrue(optionalBookingAggregate.isPresent());

        checkAttributeData(attributeAggregateData, optionalBookingAggregate.get());
        optionalBookingAggregate = bookingAggregateApplicationService.findById(bookingAggregate.getId());
        assertTrue(optionalBookingAggregate.isPresent());
        checkAttributeData(attributeAggregateData, optionalBookingAggregate.get());
    }

    private void checkAttributeData(BookingAggregateAttributeData attributeData, BookingAggregate actualEntity) {
        assertEquals(attributeData.getTitle(), actualEntity.getBooking().getTitle());
        assertEquals(attributeData.getMoney(), actualEntity.getBooking().getMoney());
        assertEquals(attributeData.getBookingCategory(), actualEntity.getCategory());
    }

}
