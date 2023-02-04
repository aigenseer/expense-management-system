package de.dhbw.cleanproject.application.financialledger;

import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FinancialLedgerApplicationService {

    private final FinancialLedgerRepository repository;

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
        FinancialLedger financialLedger = FinancialLedger.builder().id(UUID.randomUUID()).build();
        return updateByAttributeData(financialLedger, data);
    }

    public Optional<FinancialLedger> updateByAttributeData(FinancialLedger financialLedger, FinancialLedgerAttributeData data){
        financialLedger.setName(data.getName());
        return Optional.of(save(financialLedger));
    }

}
