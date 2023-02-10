package de.dhbw.ems.application.mediator;

import de.dhbw.ems.application.booking.BookingApplicationService;
import de.dhbw.ems.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.ems.application.mediator.service.UserOperationService;
import de.dhbw.ems.application.user.UserApplicationService;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
    private BookingApplicationService bookingApplicationService;

    private final UUID userId = UUID.fromString("12345678-1234-1234-a123-123456789001");
    private final UUID financialLedgerId = UUID.fromString("12345678-1234-1234-a123-123456789011");
    private final UUID bookingId = UUID.fromString("12345678-1234-1234-a123-123456789031");


    @Test
    public void testDelete(){
        boolean result = userOperationService.delete(userId);
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
