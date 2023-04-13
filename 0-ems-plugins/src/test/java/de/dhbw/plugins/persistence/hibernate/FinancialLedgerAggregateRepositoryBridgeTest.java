package de.dhbw.plugins.persistence.hibernate;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregate;
import de.dhbw.plugins.persistence.hibernate.financialledger.aggregate.FinancialLedgerAggregateRepositoryBridge;
import de.dhbw.plugins.persistence.hibernate.financialledger.aggregate.SpringDataFinancialLedgerAggregateRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@ComponentScan("de.dhbw.plugins.persistence.hibernate")
public class FinancialLedgerAggregateRepositoryBridgeTest {

    @Mock
    private SpringDataFinancialLedgerAggregateRepository springDataRepository;

    @InjectMocks
    private FinancialLedgerAggregateRepositoryBridge repositoryBridge;

    private final FinancialLedgerAggregate entity = FinancialLedgerAggregate.builder().id(UUID.randomUUID()).build();
    private final List<FinancialLedgerAggregate> entities = new ArrayList<FinancialLedgerAggregate>(){{ add(entity); }};

    @Before()
    public void setup(){
        when(springDataRepository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(springDataRepository.findAll()).thenReturn(entities);
    }

    @Test
    public void testSave() {
        repositoryBridge.save(entity);
        verify(springDataRepository).save(entity);
    }

    @Test
    public void testDeleteById() {
        repositoryBridge.deleteById(entity.getId());
        verify(springDataRepository).deleteById(entity.getId());
    }

    @Test
    public void testFindAll(){
        List<FinancialLedgerAggregate> result = repositoryBridge.findAll();
        assertEquals(1, result.size());
        assertTrue(result.contains(entity));
        verify(springDataRepository).findAll();
    }

    @Test
    public void testFindById(){
        Optional<FinancialLedgerAggregate> result = repositoryBridge.findById(entity.getId());
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(springDataRepository).findById(entity.getId());
    }

}
