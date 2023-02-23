package de.dhbw.ems.application.mediator;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.abstractioncode.valueobject.money.Money;
import de.dhbw.ems.application.booking.aggregate.BookingAggregateApplicationService;
import de.dhbw.ems.application.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.application.bookingcategory.BookingCategoryApplicationService;
import de.dhbw.ems.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.ems.application.mediator.service.BookingOperationService;
import de.dhbw.ems.application.user.UserApplicationService;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.booking.reference.BookingReference;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
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


@RunWith(SpringRunner.class)
@DataJpaTest()
@ComponentScan("de.dhbw")
public class BookingAggregateOperationServiceTest {

    @Autowired
    private BookingOperationService bookingOperationService;
    @Autowired
    private UserApplicationService userApplicationService;
    @Autowired
    private FinancialLedgerApplicationService financialLedgerApplicationService;
    @Autowired
    private BookingCategoryApplicationService bookingCategoryApplicationService;
    @Autowired
    private BookingAggregateApplicationService bookingAggregateApplicationService;

    private final UUID userId = UUID.fromString("12345678-1234-1234-a123-123456789001");
    private final UUID userId2 = UUID.fromString("12345678-1234-1234-a123-123456789002");
    private final UUID financialLedgerId = UUID.fromString("12345678-1234-1234-a123-123456789011");
    private final UUID bookingCategoryId = UUID.fromString("12345678-1234-1234-a123-123456789021");
    private BookingCategory bookingCategory;
    private final UUID bookingAggregateId = UUID.fromString("12345678-1234-1234-a123-123456789331");
    private BookingAggregate bookingAggregate;

    @Before
    public void setup(){
        Optional<BookingCategory> optionalBookingCategory = bookingCategoryApplicationService.findById(bookingCategoryId);
        assertTrue(optionalBookingCategory.isPresent());
        bookingCategory = optionalBookingCategory.get();

        Optional<BookingAggregate> optionalBooking =  bookingAggregateApplicationService.findById(bookingAggregateId);
        assertTrue(optionalBooking.isPresent());
        bookingAggregate = optionalBooking.get();
    }

    @Test
    public void testFind() {
        Optional<BookingAggregate> optionalBooking = bookingOperationService.find(userId, financialLedgerId, bookingAggregateId);
        assertTrue(optionalBooking.isPresent());
        assertEquals(bookingAggregate, optionalBooking.get());
    }

    @Test
    public void testAdd() {
        BookingAggregateAttributeData attributeData = BookingAggregateAttributeData.builder()
                .title("Example-Booking-3")
                .bookingCategory(bookingCategory)
                .money(new Money(19.00, CurrencyType.EURO))
                .build();

        Optional<BookingAggregate> optionalBookingAggregate = bookingOperationService.create(userId, financialLedgerId, attributeData);
        assertTrue(optionalBookingAggregate.isPresent());
        assertEquals(attributeData.getTitle(), optionalBookingAggregate.get().getBooking().getTitle());
        assertEquals(financialLedgerId, optionalBookingAggregate.get().getFinancialLedgerId());
        assertEquals(userId, optionalBookingAggregate.get().getCreatorId());
    }

    @Test
    public void testExist() {
        boolean result = bookingOperationService.exists(userId, financialLedgerId, bookingAggregateId);
        assertTrue(result);
    }

    @Test
    public void testDelete() {
        boolean result = bookingOperationService.delete(userId, financialLedgerId, bookingAggregateId);
        assertTrue(result);
        Optional<BookingAggregate> optionalBooking = bookingAggregateApplicationService.findById(bookingAggregateId);
        assertFalse(optionalBooking.isPresent());

        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        optionalBooking = optionalFinancialLedger.get().getBookingAggregates().stream().filter(f -> f.getBooking().getId().equals(bookingAggregateId)).findFirst();
        assertFalse(optionalBooking.isPresent());

        Optional<BookingCategory> optionalBookingCategory = bookingCategoryApplicationService.findById(bookingCategoryId);
        assertTrue(optionalBookingCategory.isPresent());
        optionalBooking = optionalBookingCategory.get().getBookingAggregates().stream().filter(f -> f.getBooking().getId().equals(bookingAggregateId)).findFirst();
        assertFalse(optionalBooking.isPresent());
    }


    @Test
    public void testReferenceUser() {
        boolean result = bookingOperationService.referenceUser(userId, financialLedgerId, bookingAggregateId, userId2);
        assertTrue(result);

        Optional<User> optionalUser = userApplicationService.findById(userId2);
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();

        Optional<BookingAggregate> optionalBookingAggregate = bookingAggregateApplicationService.findById(bookingAggregateId);
        assertTrue(optionalBookingAggregate.isPresent());

        Optional<BookingReference> optionalBookingReference = optionalBookingAggregate.get().getBookingReferences().stream().filter(r -> r.getId().getUserId().equals(user.getId())).findFirst();
        assertTrue(optionalBookingReference.isPresent());
    }

    @Test
    public void testDeleteUserReference() {
        boolean result = bookingOperationService.deleteUserReference(userId2, financialLedgerId, bookingAggregateId);
        assertTrue(result);
        Optional<BookingAggregate> optionalBookingAggregate = bookingAggregateApplicationService.findById(bookingAggregateId);
        assertTrue(optionalBookingAggregate.isPresent());
        Optional<User> optionalUser = optionalBookingAggregate.get().getReferencedUsers().stream().filter(f -> f.getId().equals(userId2)).findFirst();
        assertFalse(optionalUser.isPresent());
    }

}
