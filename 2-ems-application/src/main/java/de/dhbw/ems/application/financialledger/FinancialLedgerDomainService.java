package de.dhbw.ems.application.financialledger;

import de.dhbw.ems.domain.financialledger.FinancialLedger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerDomainService {

    List<FinancialLedger> findAll();

    Optional<FinancialLedger> findById(UUID id);

    FinancialLedger save(FinancialLedger financialLedger);

    void deleteById(UUID id);

    Optional<FinancialLedger> createByAttributeData(FinancialLedgerAttributeData data);

    Optional<FinancialLedger> updateByAttributeData(FinancialLedger financialLedger, FinancialLedgerAttributeData data);

}
