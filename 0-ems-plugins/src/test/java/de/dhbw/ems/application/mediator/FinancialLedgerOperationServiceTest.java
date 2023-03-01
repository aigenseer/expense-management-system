package de.dhbw.ems.application.mediator;

import de.dhbw.ems.application.booking.aggregate.BookingAggregateApplicationService;
import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryApplicationService;
import de.dhbw.ems.application.financialledger.aggregate.FinancialLedgerAggregateApplicationService;
import de.dhbw.ems.application.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.application.financialledger.link.UserFinancialLedgerLinkApplicationService;
import de.dhbw.ems.application.mediator.service.FinancialLedgerOperationService;
import de.dhbw.ems.application.user.UserApplicationService;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerLink;
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
public class FinancialLedgerOperationServiceTest {

    @Autowired
    private FinancialLedgerOperationService financialLedgerOperationService;
    @Autowired
    private UserFinancialLedgerLinkApplicationService userFinancialLedgerLinkApplicationService;
    @Autowired
    private UserApplicationService userApplicationService;
    @Autowired
    private FinancialLedgerAggregateApplicationService financialLedgerAggregateApplicationService;
    @Autowired
    private BookingCategoryApplicationService bookingCategoryApplicationService;
    @Autowired
    private BookingAggregateApplicationService bookingAggregateApplicationService;

    private final UUID userId = UUID.fromString("12345678-1234-1234-a123-123456789001");
    private final UUID financialLedgerAggregateId = UUID.fromString("12345678-1234-1234-a123-123456789111");
    private FinancialLedgerAggregate financialLedgerAggregate;
    private final UUID bookingCategoryAggregateId = UUID.fromString("12345678-1234-1234-a123-123456789221");
    private final UUID bookingAggregateId = UUID.fromString("12345678-1234-1234-a123-123456789331");

    @Before
    public void setup(){
        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate =  financialLedgerAggregateApplicationService.findById(financialLedgerAggregateId);
        assertTrue(optionalFinancialLedgerAggregate.isPresent());
        financialLedgerAggregate = optionalFinancialLedgerAggregate.get();
    }

    @Test
    public void testFind() {
        Optional<FinancialLedgerAggregate> optionalFinancialLedger = financialLedgerOperationService.find(userId, financialLedgerAggregateId);
        assertTrue(optionalFinancialLedger.isPresent());
        assertEquals(financialLedgerAggregate, optionalFinancialLedger.get());
    }

    @Test
    public void testHasUserPermission() {
        boolean result = financialLedgerOperationService.hasUserPermission(userId, financialLedgerAggregateId);
        assertTrue(result);
    }

    @Test
    public void testAppendUser() {
        boolean result = financialLedgerOperationService.appendUser(userId, financialLedgerAggregateId);
        assertTrue(result);
        Optional<User> optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        Optional<UserFinancialLedgerLink> optionalUserFinancialLedgerLink = userFinancialLedgerLinkApplicationService.findById(userId, financialLedgerAggregateId);
        assertTrue(optionalUserFinancialLedgerLink.isPresent());
        assertEquals(financialLedgerAggregate, optionalUserFinancialLedgerLink.get().getFinancialLedgerAggregate());
    }

    @Test
    public void testCreate() {
        FinancialLedgerAttributeData attributeData = FinancialLedgerAttributeData.builder().name("Example-Financial-Ledger-3").build();
        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerOperationService.create(userId, attributeData);
        assertTrue(optionalFinancialLedgerAggregate.isPresent());
        assertEquals(attributeData.getName(), optionalFinancialLedgerAggregate.get().getFinancialLedger().getTitle());
        Optional<UserFinancialLedgerLink> optionalUserFinancialLedgerLink = optionalFinancialLedgerAggregate.get().getUserFinancialLedgerLinks().stream().filter(u -> u.getId().getUserId().equals(userId)).findFirst();
        assertTrue(optionalUserFinancialLedgerLink.isPresent());
    }

    @Test
    public void testUnlinkUser(){
        boolean result = financialLedgerOperationService.unlinkUser(userId, financialLedgerAggregateId);
        assertTrue(result);

        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerAggregateApplicationService.findById(financialLedgerAggregateId);
        assertTrue(optionalFinancialLedgerAggregate.isPresent());
        Optional<User> optionalUser = optionalFinancialLedgerAggregate.get().getAuthorizedUser().stream().filter(f -> f.getId().equals(userId)).findFirst();
        assertFalse(optionalUser.isPresent());

        optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        Optional<UserFinancialLedgerLink> optionalUserFinancialLedgerLink = userFinancialLedgerLinkApplicationService.findById(userId, financialLedgerAggregateId);
        assertFalse(optionalUserFinancialLedgerLink.isPresent());
    }

    @Test
    public void testDelete() {
        boolean result = financialLedgerOperationService.delete(userId, financialLedgerAggregateId);
        assertTrue(result);
        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerAggregateApplicationService.findById(financialLedgerAggregateId);
        assertFalse(optionalFinancialLedgerAggregate.isPresent());

        Optional<User> optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        Optional<UserFinancialLedgerLink> optionalUserFinancialLedgerLink = userFinancialLedgerLinkApplicationService.findById(userId, financialLedgerAggregateId);
        assertFalse(optionalUserFinancialLedgerLink.isPresent());

        Optional<BookingAggregate> optionalBooking = bookingAggregateApplicationService.findById(bookingAggregateId);
        assertFalse(optionalBooking.isPresent());

        Optional<BookingCategory> optionalBookingCategory = bookingCategoryApplicationService.findById(bookingCategoryAggregateId);
        assertFalse(optionalBookingCategory.isPresent());
    }


}
