package de.dhbw.cleanproject.application.mediator;

import de.dhbw.cleanproject.application.booking.BookingApplicationService;
import de.dhbw.cleanproject.application.bookingcategory.BookingCategoryApplicationService;
import de.dhbw.cleanproject.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.cleanproject.application.financialledger.FinancialLedgerAttributeData;
import de.dhbw.cleanproject.application.mediator.service.FinancialLedgerOperationService;
import de.dhbw.cleanproject.application.user.UserApplicationService;
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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


@RunWith(SpringRunner.class)
@DataJpaTest()
@ComponentScan("de.dhbw")
public class FinancialLedgerOperationServiceTest {

    @Autowired
    private FinancialLedgerOperationService financialLedgerOperationService;
    @Autowired
    private UserApplicationService userApplicationService;
    @Autowired
    private FinancialLedgerApplicationService financialLedgerApplicationService;
    @Autowired
    private BookingCategoryApplicationService bookingCategoryApplicationService;
    @Autowired
    private BookingApplicationService bookingApplicationService;

    private final UUID userId = UUID.fromString("12345678-1234-1234-a123-123456789001");
    private final UUID financialLedgerId = UUID.fromString("12345678-1234-1234-a123-123456789011");
    private FinancialLedger financialLedger;
    private final UUID bookingCategoryId = UUID.fromString("12345678-1234-1234-a123-123456789021");
    private final UUID bookingId = UUID.fromString("12345678-1234-1234-a123-123456789031");

    @Before
    public void setup(){
        Optional<FinancialLedger> optionalFinancialLedger =  financialLedgerApplicationService.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        financialLedger = optionalFinancialLedger.get();

    }

    @Test
    public void testFind() {
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerOperationService.find(userId, financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        assertEquals(financialLedger, optionalFinancialLedger.get());
    }

    @Test
    public void testHasUserPermission() {
        boolean result = financialLedgerOperationService.hasUserPermission(userId, financialLedgerId);
        assertTrue(result);
    }

    @Test
    public void testAppendUser() {
        boolean result = financialLedgerOperationService.appendUser(userId, financialLedgerId);
        assertTrue(result);
        Optional<User> optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        Optional<FinancialLedger> optionalFinancialLedger = optionalUser.get().getFinancialLedgers().stream().filter(f -> f.getId().equals(financialLedgerId)).findFirst();
        assertTrue(optionalFinancialLedger.isPresent());
        assertEquals(financialLedger, optionalFinancialLedger.get());
    }

    @Test
    public void testCreate() {
        FinancialLedgerAttributeData attributeData = FinancialLedgerAttributeData.builder().name("Example-Financial-Ledger-3").build();
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerOperationService.create(userId, attributeData);
        assertTrue(optionalFinancialLedger.isPresent());
        assertEquals(attributeData.getName(), optionalFinancialLedger.get().getName());

        Optional<User> optionalUser = userApplicationService.findById(userId);
        assertTrue(optionalUser.isPresent());
        Optional<FinancialLedger> optionalFoundFinancialLedger = optionalUser.get().getFinancialLedgers().stream().filter(f -> f.getId().equals(optionalFinancialLedger.get().getId())).findFirst();
        assertTrue(optionalFoundFinancialLedger.isPresent());
        assertEquals(optionalFinancialLedger.get().getId(), optionalFoundFinancialLedger.get().getId());
    }

    @Test
    public void testUnlinkUser(){
        boolean result = financialLedgerOperationService.unlinkUser(userId, financialLedgerId);
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
    public void testDelete() {
        boolean result = financialLedgerOperationService.delete(userId, financialLedgerId);
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


}
