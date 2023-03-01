package de.dhbw.ems.domain.financialledger.aggregate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerAggregateRepository {

    List<FinancialLedgerAggregate> findAll();

    Optional<FinancialLedgerAggregate> findById(UUID id);

    FinancialLedgerAggregate save(FinancialLedgerAggregate user);

    void deleteById(UUID id);


}
