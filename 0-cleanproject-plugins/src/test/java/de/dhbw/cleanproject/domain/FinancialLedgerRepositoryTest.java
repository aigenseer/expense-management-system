package de.dhbw.cleanproject.domain;

import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedgerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest()
@ComponentScan("de.dhbw")
public class FinancialLedgerRepositoryTest {

    @Autowired
    private FinancialLedgerRepository repository;

    private final FinancialLedger entity1 = FinancialLedger.builder()
            .id(UUID.fromString("12345678-1234-1234-a123-123456789011"))
            .name("Example-Financial-Ledger")
        .build();

    private final FinancialLedger entity2 = FinancialLedger.builder()
            .id(UUID.fromString("12345678-1234-1234-a123-123456789012"))
            .name("Example-Financial-Ledger-2")
            .build();

    @Test
    public void testSave() {
        repository.save(entity2);
        Optional<FinancialLedger> result = repository.findById(entity2.getId());
        assertTrue(result.isPresent());
        checkEntity(entity2, result.get());
    }

    @Test
    public void testDeleteById() {
        repository.deleteById(entity1.getId());
        Optional<FinancialLedger> result = repository.findById(entity1.getId());
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindAll(){
        List<FinancialLedger> resultList = repository.findAll();
        assertEquals(1, resultList.size());
        Optional<FinancialLedger> result = resultList.stream().filter(user -> user.getId().equals( entity1.getId())).findFirst();
        assertTrue(result.isPresent());
        checkEntity(entity1, result.get());
    }

    @Test
    public void testFindById(){
        Optional<FinancialLedger> result = repository.findById(entity1.getId());
        assertTrue(result.isPresent());
        checkEntity(entity1, result.get());
    }

    private void checkEntity(FinancialLedger expectedEntity, FinancialLedger actualEntity){
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getName(), actualEntity.getName());
    }

}
