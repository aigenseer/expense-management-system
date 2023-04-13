package de.dhbw.plugins.persistence.hibernate.financialledger.aggregate;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregate;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class FinancialLedgerAggregateRepositoryBridge implements FinancialLedgerAggregateRepository {

    private final SpringDataFinancialLedgerAggregateRepository springDataFinancialLedgerAggregateRepository;

    @Override
    public List<FinancialLedgerAggregate> findAll() {
        return springDataFinancialLedgerAggregateRepository.findAll();
    }

    @Override
    public Optional<FinancialLedgerAggregate> findById(UUID id) {
        return springDataFinancialLedgerAggregateRepository.findById(id);
    }

    @Override
    public FinancialLedgerAggregate save(FinancialLedgerAggregate user) {
        return springDataFinancialLedgerAggregateRepository.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        springDataFinancialLedgerAggregateRepository.deleteById(id);
    }


}
