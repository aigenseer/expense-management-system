package de.dhbw.ems.application.domain.service.financialledger.entity;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FinancialLedgerApplicationService implements FinancialLedgerDomainService {

    private final FinancialLedgerAggregateRepository repository;

    public List<FinancialLedger> findAll() {
        return repository.findAll();
    }

    public Optional<FinancialLedger> findById(UUID id) {
        return repository.findById(id);
    }

    public FinancialLedger save(FinancialLedger financialLedger) {
        return repository.save(financialLedger);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Optional<FinancialLedger> createByAttributeData(FinancialLedgerAttributeData data){
        FinancialLedger financialLedger = FinancialLedger.builder()
                .id(UUID.randomUUID())
                .userFinancialLedgerLinks(new HashSet<>())
                .title(data.getTitle())
                .build();
        return updateByAttributeData(financialLedger, data);
    }

    public Optional<FinancialLedger> updateByAttributeData(FinancialLedger financialLedger, FinancialLedgerAttributeData data){
        financialLedger.setTitle(data.getTitle());
        return Optional.of(save(financialLedger));
    }

}
