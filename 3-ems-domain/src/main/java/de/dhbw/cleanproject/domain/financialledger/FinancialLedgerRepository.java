package de.dhbw.cleanproject.domain.financialledger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerRepository {

    List<FinancialLedger> findAll();

    Optional<FinancialLedger> findById(UUID id);

    FinancialLedger save(FinancialLedger user);

    void deleteById(UUID id);


}
