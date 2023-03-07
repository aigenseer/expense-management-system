package de.dhbw.ems.application.domain.service.financialledger.aggregate;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FinancialLedgerAggregateApplicationService implements FinancialLedgerAggregateDomainService {

    private final FinancialLedgerAggregateRepository repository;

    public List<FinancialLedgerAggregate> findAll() {
        return repository.findAll();
    }

    public Optional<FinancialLedgerAggregate> findById(UUID id) {
        return repository.findById(id);
    }

    public FinancialLedgerAggregate save(FinancialLedgerAggregate financialLedgerAggregate) {
        return repository.save(financialLedgerAggregate);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Optional<FinancialLedgerAggregate> createByAttributeData(FinancialLedgerAttributeData data){
        FinancialLedgerAggregate financialLedgerAggregate = FinancialLedgerAggregate.builder()
                .id(UUID.randomUUID())
                .userFinancialLedgerLinks(new HashSet<>())
                .build();
        return updateByAttributeData(financialLedgerAggregate, data);
    }

    public Optional<FinancialLedgerAggregate> updateByAttributeData(FinancialLedgerAggregate financialLedgerAggregate, FinancialLedgerAttributeData data){
        financialLedgerAggregate.setTitle(data.getName());
        return Optional.of(save(financialLedgerAggregate));
    }

}
