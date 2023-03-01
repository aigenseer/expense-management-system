package de.dhbw.ems.application;

import de.dhbw.ems.application.financialledger.aggregate.FinancialLedgerAggregateApplicationService;
import de.dhbw.ems.application.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import org.junit.Before;
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
public class FinancialLedgerAggregateApplicationServiceTest {

    @Autowired
    private FinancialLedgerAggregateApplicationService financialLedgerAggregateApplicationService;

    private FinancialLedgerAggregate financialLedgerAggregate;
    private final FinancialLedgerAttributeData attributeData = FinancialLedgerAttributeData.builder()
            .name("Name")
            .build();

    @Before
    public void setup(){
        Optional<FinancialLedgerAggregate> optionalFinancialLedger = financialLedgerAggregateApplicationService.findById(UUID.fromString("12345678-1234-1234-a123-123456789111"));
        assertTrue(optionalFinancialLedger.isPresent());
        financialLedgerAggregate = optionalFinancialLedger.get();
    }

    @Test
    public void testSave() {
        FinancialLedgerAggregate financialLedgerAggregate2 = FinancialLedgerAggregate.builder()
                .id(UUID.randomUUID())
                .financialLedgerId(financialLedgerAggregate.getFinancialLedgerId())
                .financialLedger(financialLedgerAggregate.getFinancialLedger())
                .build();

        financialLedgerAggregateApplicationService.save(financialLedgerAggregate2);
        Optional<FinancialLedgerAggregate> result = financialLedgerAggregateApplicationService.findById(financialLedgerAggregate.getId());
        assertTrue(result.isPresent());
        checkAggregate(financialLedgerAggregate, result.get());
    }

    @Test
    public void testDeleteById() {
        financialLedgerAggregateApplicationService.deleteById(financialLedgerAggregate.getId());
        Optional<FinancialLedgerAggregate> result = financialLedgerAggregateApplicationService.findById(financialLedgerAggregate.getId());
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindAll() {
        List<FinancialLedgerAggregate> resultList = financialLedgerAggregateApplicationService.findAll();
        assertEquals(1, resultList.size());
        Optional<FinancialLedgerAggregate> result = resultList.stream().filter(user -> user.getId().equals(financialLedgerAggregate.getId())).findFirst();
        assertTrue(result.isPresent());
        checkAggregate(financialLedgerAggregate, result.get());
    }

    @Test
    public void testFindById() {
        Optional<FinancialLedgerAggregate> result = financialLedgerAggregateApplicationService.findById(financialLedgerAggregate.getId());
        assertTrue(result.isPresent());
        checkAggregate(financialLedgerAggregate, result.get());
    }

    private void checkAggregate(FinancialLedgerAggregate expectedAggregate, FinancialLedgerAggregate actualAggregate) {
        assertEquals(expectedAggregate.getId(), actualAggregate.getId());
        assertEquals(expectedAggregate.getFinancialLedger().getId(), actualAggregate.getFinancialLedger().getId());
        assertEquals(expectedAggregate.getFinancialLedger().getTitle(), actualAggregate.getFinancialLedger().getTitle());
    }

    @Test
    public void testCreateByAttributeData() {
        Optional<FinancialLedgerAggregate> optionalFinancialLedger = financialLedgerAggregateApplicationService.createByAttributeData(attributeData);
        assertTrue(optionalFinancialLedger.isPresent());
        checkAttributeData(attributeData, optionalFinancialLedger.get());
    }

    @Test
    public void testUpdateByAttributeData() {
        Optional<FinancialLedgerAggregate> optionalFinancialLedger = financialLedgerAggregateApplicationService.updateByAttributeData(financialLedgerAggregate, attributeData);
        assertTrue(optionalFinancialLedger.isPresent());
        checkAttributeData(attributeData, optionalFinancialLedger.get());
        optionalFinancialLedger = financialLedgerAggregateApplicationService.findById(financialLedgerAggregate.getId());
        assertTrue(optionalFinancialLedger.isPresent());
        checkAttributeData(attributeData, optionalFinancialLedger.get());
    }

    private void checkAttributeData(FinancialLedgerAttributeData attributeData, FinancialLedgerAggregate actualAggregate) {
        assertEquals(attributeData.getName(), actualAggregate.getFinancialLedger().getTitle());
    }

}
