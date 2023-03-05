package de.dhbw.ems.application.domain.service.financialledger.aggregate;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.application.domain.service.financialledger.entity.FinancialLedgerDomainService;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregateRepository;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
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
    private final FinancialLedgerDomainService financialLedgerDomainService;

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
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerDomainService.createByAttributeData(data);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        FinancialLedgerAggregate financialLedgerAggregate = FinancialLedgerAggregate.builder()
                .id(UUID.randomUUID())
                .financialLedger(optionalFinancialLedger.get())
                .financialLedgerId(optionalFinancialLedger.get().getId())
                .userFinancialLedgerLinks(new HashSet<>())
                .build();
        return Optional.of(save(financialLedgerAggregate));
    }

    public Optional<FinancialLedgerAggregate> updateByAttributeData(FinancialLedgerAggregate financialLedgerAggregate, FinancialLedgerAttributeData data){
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerDomainService.updateByAttributeData(financialLedgerAggregate.getFinancialLedger(), data);
        if (!optionalFinancialLedger.isPresent()) return Optional.empty();
        return findById(financialLedgerAggregate.getId());
    }

}
