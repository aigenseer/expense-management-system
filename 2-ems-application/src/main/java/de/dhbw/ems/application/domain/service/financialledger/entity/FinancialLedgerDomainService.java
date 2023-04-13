package de.dhbw.ems.application.domain.service.financialledger.entity;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerData;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerDomainService extends FinancialLedgerDomainServicePort {

    List<FinancialLedger> findAll();

    Optional<FinancialLedger> findById(UUID id);

    FinancialLedger save(FinancialLedger financialLedger);

    void deleteById(UUID id);

    Optional<FinancialLedger> createByAttributeData(FinancialLedgerData data);

}
