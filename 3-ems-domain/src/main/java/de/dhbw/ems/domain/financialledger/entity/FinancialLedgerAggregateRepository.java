package de.dhbw.ems.domain.financialledger.entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerAggregateRepository {

    List<FinancialLedger> findAll();

    Optional<FinancialLedger> findById(UUID id);

    FinancialLedger save(FinancialLedger user);

    void deleteById(UUID id);


}
