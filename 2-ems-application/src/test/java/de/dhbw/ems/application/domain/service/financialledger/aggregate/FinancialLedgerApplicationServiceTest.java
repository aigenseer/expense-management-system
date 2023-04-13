package de.dhbw.ems.application.domain.service.financialledger.aggregate;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.application.domain.service.financialledger.entity.FinancialLedgerApplicationService;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
public class FinancialLedgerApplicationServiceTest {

    private final FinancialLedgerRepository repository = Mockito.mock(FinancialLedgerRepository.class);
    private FinancialLedgerApplicationService aggregateApplicationService;

    private final FinancialLedger aggregate = FinancialLedger.builder()
            .id(UUID.randomUUID())
            .title("Example-Financial-Ledger")
            .build();

    private final FinancialLedgerAttributeData attributeData = FinancialLedgerAttributeData.builder()
            .title(aggregate.getTitle())
            .build();

    private final List<FinancialLedger> aggregates = new ArrayList<FinancialLedger>(){{ add(aggregate); }};

    @Before
    public void setup(){
        aggregateApplicationService = new FinancialLedgerApplicationService(repository);
        when(repository.save(aggregate)).thenReturn(aggregate);
        when(repository.findAll()).thenReturn(aggregates);
        when(repository.findById(aggregate.getId())).thenReturn(Optional.of(aggregate));
    }

    @Test
    public void testSave() {
        aggregateApplicationService.save(aggregate);
        verify(repository).save(aggregate);
    }

    @Test
    public void testDeleteById() {
        aggregateApplicationService.deleteById(aggregate.getId());
        verify(repository).deleteById(aggregate.getId());
    }

    @Test
    public void testFindAll() {
        List<FinancialLedger> resultList = aggregateApplicationService.findAll();
        assertEquals(1, resultList.size());
        Optional<FinancialLedger> result = resultList.stream().filter(user -> user.getId().equals(aggregate.getId())).findFirst();
        assertTrue(result.isPresent());
        checkAggregate(aggregate, result.get());
    }

    @Test
    public void testFindById() {
        Optional<FinancialLedger> result = aggregateApplicationService.findById(aggregate.getId());
        assertTrue(result.isPresent());
        checkAggregate(aggregate, result.get());
    }

    private void checkAggregate(FinancialLedger expectedAggregate, FinancialLedger actualAggregate) {
        assertEquals(expectedAggregate.getId(), actualAggregate.getId());
        assertEquals(expectedAggregate.getTitle(), actualAggregate.getTitle());
    }

    @Test
    public void testCreateByAttributeData() {
        when(repository.save(any())).thenReturn(aggregate);

        Optional<FinancialLedger> optionalFinancialLedger = aggregateApplicationService.createByAttributeData(attributeData);
        assertTrue(optionalFinancialLedger.isPresent());

        verify(repository).save(argThat(aggregate -> {
            checkAttributeData(attributeData, aggregate);
            return true;
        }));
    }

    @Test
    public void testUpdateByAttributeData() {
        when(repository.save(any())).thenReturn(aggregate);
        Optional<FinancialLedger> optionalFinancialLedger = aggregateApplicationService.updateByAttributeData(aggregate, attributeData);
        assertTrue(optionalFinancialLedger.isPresent());
        verify(repository).save(argThat(aggregate -> {
            checkAttributeData(attributeData, aggregate);
            return true;
        }));
    }

    private void checkAttributeData(FinancialLedgerAttributeData attributeData, FinancialLedger actualAggregate) {
        assertEquals(attributeData.getTitle(), actualAggregate.getTitle());
    }

}
