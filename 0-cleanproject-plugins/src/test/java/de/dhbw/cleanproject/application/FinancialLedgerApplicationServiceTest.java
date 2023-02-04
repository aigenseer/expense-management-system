package de.dhbw.cleanproject.application;

import de.dhbw.cleanproject.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.cleanproject.application.financialledger.FinancialLedgerAttributeData;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
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
public class FinancialLedgerApplicationServiceTest {

    @Autowired
    private FinancialLedgerApplicationService applicationService;

    private final FinancialLedger entity1 = FinancialLedger.builder()
            .id(UUID.fromString("12345678-1234-1234-a123-123456789011"))
            .name("Example-Financial-Ledger")
            .build();

    private final FinancialLedger entity2 = FinancialLedger.builder()
            .id(UUID.fromString("12345678-1234-1234-a123-123456789012"))
            .name("Example-Financial-Ledger-2")
            .build();

    private final FinancialLedgerAttributeData attributeData = FinancialLedgerAttributeData.builder()
            .name("Name")
            .build();

    @Test
    public void testSave() {
        applicationService.save(entity2);
        Optional<FinancialLedger> result = applicationService.findById(entity2.getId());
        assertTrue(result.isPresent());
        checkEntity(entity2, result.get());
    }

    @Test
    public void testDeleteById() {
        applicationService.deleteById(entity1.getId());
        Optional<FinancialLedger> result = applicationService.findById(entity1.getId());
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindAll() {
        List<FinancialLedger> resultList = applicationService.findAll();
        assertEquals(1, resultList.size());
        Optional<FinancialLedger> result = resultList.stream().filter(user -> user.getId().equals(entity1.getId())).findFirst();
        assertTrue(result.isPresent());
        checkEntity(entity1, result.get());
    }

    @Test
    public void testFindById() {
        Optional<FinancialLedger> result = applicationService.findById(entity1.getId());
        assertTrue(result.isPresent());
        checkEntity(entity1, result.get());
    }

    private void checkEntity(FinancialLedger expectedEntity, FinancialLedger actualEntity) {
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getName(), actualEntity.getName());
    }

    @Test
    public void testCreateByAttributeData() {
        Optional<FinancialLedger> optionalFinancialLedger = applicationService.createByAttributeData(attributeData);
        assertTrue(optionalFinancialLedger.isPresent());
        checkAttributeData(attributeData, optionalFinancialLedger.get());
    }

    @Test
    public void testUpdateByAttributeData() {
        Optional<FinancialLedger> optionalFinancialLedger = applicationService.updateByAttributeData(entity1, attributeData);
        assertTrue(optionalFinancialLedger.isPresent());
        checkAttributeData(attributeData, optionalFinancialLedger.get());
        optionalFinancialLedger = applicationService.findById(entity1.getId());
        assertTrue(optionalFinancialLedger.isPresent());
        checkAttributeData(attributeData, optionalFinancialLedger.get());
    }

    private void checkAttributeData(FinancialLedgerAttributeData attributeData, FinancialLedger actualEntity) {
        assertEquals(attributeData.getName(), actualEntity.getName());
    }

}
