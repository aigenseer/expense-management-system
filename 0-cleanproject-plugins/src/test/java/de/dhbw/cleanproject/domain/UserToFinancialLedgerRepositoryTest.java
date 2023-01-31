package de.dhbw.cleanproject.domain;

import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedgerRepository;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.cleanproject.domain.user.UserRepository;
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
public class UserToFinancialLedgerRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FinancialLedgerRepository financialLedgerRepository;

    private final UUID financialLedgerId = UUID.fromString("12345678-1234-1234-a123-123456789011");
    private FinancialLedger financialLedger;

    private final UUID userId = UUID.fromString("12345678-1234-1234-a123-123456789001");
    private User user;

    @Before()
    public void setup(){
        Optional<User> optionalUser = userRepository.findById(userId);
        assertTrue(optionalUser.isPresent());
        user = optionalUser.get();

        Optional<FinancialLedger> optionalFinancialLedger =  financialLedgerRepository.findById(financialLedgerId);
        assertTrue(optionalFinancialLedger.isPresent());
        financialLedger = optionalFinancialLedger.get();
    }


    @Test
    public void testFindReferences() {
        Optional<FinancialLedger> optionalFinancialLedger = user.getFinancialLedgers().stream().filter(f -> f.getId().equals(financialLedgerId)).findFirst();
        assertTrue(optionalFinancialLedger.isPresent());

        Optional<User> optionalUser = financialLedger.getAuthorizedUser().stream().filter(u -> u.getId().equals(userId)).findFirst();
        assertTrue(optionalUser.isPresent());
    }

    @Test
    public void testDeleteReferences() {
        Optional<User> optionalUser = userRepository.findById(this.user.getId());
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();

        Optional<FinancialLedger> optionalFinancialLedger = user.getFinancialLedgers().stream().filter(financialLedger -> financialLedger.getId().equals(financialLedgerId)).findFirst();
        assertTrue(optionalFinancialLedger.isPresent());
        FinancialLedger financialLedger = optionalFinancialLedger.get();

        user.getFinancialLedgers().remove(financialLedger);
        userRepository.save(user);

        optionalUser = userRepository.findById(user.getId());
        assertTrue(optionalUser.isPresent());
        assertEquals(0, optionalUser.get().getFinancialLedgers().size());

    }

}
