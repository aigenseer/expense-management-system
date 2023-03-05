package de.dhbw.ems.application.domain.service.financialledger.aggregate;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.application.domain.service.financialledger.entity.FinancialLedgerDomainService;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregateRepository;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
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
public class FinancialLedgerAggregateApplicationServiceTest {

    private final FinancialLedgerAggregateRepository repository = Mockito.mock(FinancialLedgerAggregateRepository.class);
    private final FinancialLedgerDomainService domainService = Mockito.mock(FinancialLedgerDomainService.class);
    private FinancialLedgerAggregateApplicationService aggregateApplicationService;

    private final FinancialLedger entity = FinancialLedger.builder()
            .id(UUID.randomUUID())
            .title("Example-Financial-Ledger")
            .build();
    private final FinancialLedgerAggregate aggregate = FinancialLedgerAggregate.builder()
            .id(UUID.randomUUID())
            .financialLedger(entity)
            .financialLedgerId(entity.getId())
            .build();

    private final FinancialLedgerAttributeData attributeData = FinancialLedgerAttributeData.builder()
            .name(entity.getTitle())
            .build();

    private final List<FinancialLedgerAggregate> aggregates = new ArrayList<FinancialLedgerAggregate>(){{ add(aggregate); }};

    @Before
    public void setup(){
        aggregateApplicationService = new FinancialLedgerAggregateApplicationService(repository, domainService);
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
        List<FinancialLedgerAggregate> resultList = aggregateApplicationService.findAll();
        assertEquals(1, resultList.size());
        Optional<FinancialLedgerAggregate> result = resultList.stream().filter(user -> user.getId().equals(aggregate.getId())).findFirst();
        assertTrue(result.isPresent());
        checkAggregate(aggregate, result.get());
    }

    @Test
    public void testFindById() {
        Optional<FinancialLedgerAggregate> result = aggregateApplicationService.findById(aggregate.getId());
        assertTrue(result.isPresent());
        checkAggregate(aggregate, result.get());
    }

    private void checkAggregate(FinancialLedgerAggregate expectedAggregate, FinancialLedgerAggregate actualAggregate) {
        assertEquals(expectedAggregate.getId(), actualAggregate.getId());
        assertEquals(expectedAggregate.getFinancialLedger().getId(), actualAggregate.getFinancialLedger().getId());
        assertEquals(expectedAggregate.getFinancialLedger().getTitle(), actualAggregate.getFinancialLedger().getTitle());
    }

    @Test
    public void testCreateByAttributeData() {
        when(domainService.createByAttributeData(attributeData)).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(aggregate);

        Optional<FinancialLedgerAggregate> optionalFinancialLedger = aggregateApplicationService.createByAttributeData(attributeData);
        assertTrue(optionalFinancialLedger.isPresent());

        verify(domainService).createByAttributeData(attributeData);
        verify(repository).save(argThat(aggregate -> {
            checkAttributeData(attributeData, aggregate);
            return true;
        }));
    }

    @Test
    public void testUpdateByAttributeData() {
        when(domainService.updateByAttributeData(entity, attributeData)).thenReturn(Optional.of(entity));
        when(repository.findById(aggregate.getId())).thenReturn(Optional.of(aggregate));
        Optional<FinancialLedgerAggregate> optionalFinancialLedger = aggregateApplicationService.updateByAttributeData(aggregate, attributeData);
        assertTrue(optionalFinancialLedger.isPresent());
        verify(domainService).updateByAttributeData(entity, attributeData);
    }

    private void checkAttributeData(FinancialLedgerAttributeData attributeData, FinancialLedgerAggregate actualAggregate) {
        assertEquals(attributeData.getName(), actualAggregate.getFinancialLedger().getTitle());
    }

}
