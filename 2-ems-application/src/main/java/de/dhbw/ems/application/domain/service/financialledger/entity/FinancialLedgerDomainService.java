package de.dhbw.ems.application.domain.service.financialledger.entity;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerDomainService {

    List<FinancialLedger> findAll();

    Optional<FinancialLedger> findById(UUID id);

    FinancialLedger save(FinancialLedger financialLedgerAggregate);

    void deleteById(UUID id);

    Optional<FinancialLedger> createByAttributeData(FinancialLedgerAttributeData data);

    Optional<FinancialLedger> updateByAttributeData(FinancialLedger financialLedger, FinancialLedgerAttributeData data);

}
