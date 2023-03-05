package de.dhbw.ems.application.domain.service.financialledger.entity;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class FinancialLedgerApplicationServiceTest {

    @Mock
    private FinancialLedgerRepository repository;

    @InjectMocks
    private FinancialLedgerApplicationService applicationService;

    private final FinancialLedger entity = FinancialLedger.builder()
            .id(UUID.fromString("12345678-1234-1234-a123-123456789011"))
            .title("Example-Financial-Ledger")
            .build();

    private final FinancialLedgerAttributeData attributeData = FinancialLedgerAttributeData.builder()
            .name("Example-Financial-Ledger")
            .build();
    private final List<FinancialLedger> entities = new ArrayList<FinancialLedger>(){{ add(entity); }};


    @Before()
    public void setup(){
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(repository.findAll()).thenReturn(entities);
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
    }

    @Test
    public void testSave() {
        FinancialLedger result =  applicationService.save(entity);
        checkEntity(entity, result);
        verify(repository).save(entity);
    }

    @Test
    public void testDeleteById() {
        applicationService.deleteById(entity.getId());
        verify(repository).deleteById(entity.getId());
    }

    @Test
    public void testFindAll(){
        List<FinancialLedger> resultList = applicationService.findAll();
        assertEquals(1, resultList.size());
        Optional<FinancialLedger> result = resultList.stream().filter(user -> user.getId().equals(entity.getId())).findFirst();
        assertTrue(result.isPresent());
        checkEntity(entity, result.get());
        verify(repository).findAll();
    }

    @Test
    public void testFindById(){
        Optional<FinancialLedger> result = applicationService.findById(entity.getId());
        assertTrue(result.isPresent());
        checkEntity(entity, result.get());
        verify(repository).findById(entity.getId());
    }

    @Test
    public void testCreateByAttributeData(){
        when(repository.save(any())).thenReturn(entity);
        Optional<FinancialLedger> result =  applicationService.createByAttributeData(attributeData);
        assertTrue(result.isPresent());
        checkEntity(entity, result.get());
        verify(repository).save(argThat(booking -> {
            checkAttributeData(attributeData, booking);
            return true;
        }));
    }

    @Test
    public void testUpdateByEntity(){
        when(repository.save(any())).thenReturn(entity);
        Optional<FinancialLedger> result =  applicationService.updateByAttributeData(entity, attributeData);
        assertTrue(result.isPresent());
        checkEntity(entity, result.get());
        verify(repository).save(argThat(booking -> {
            checkAttributeData(attributeData, booking);
            return true;
        }));
    }

    private void checkAttributeData(FinancialLedgerAttributeData attributeData, FinancialLedger actualEntity){
        assertEquals(attributeData.getName(), actualEntity.getTitle());
    }

    private void checkEntity(FinancialLedger expectedEntity, FinancialLedger actualEntity){
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getTitle(), actualEntity.getTitle());
    }

}
