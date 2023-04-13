package de.dhbw.ems.application.domain.service.financialledger.entity;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerDomainService extends FinancialLedgerDomainServicePort {

    List<FinancialLedgerAggregate> findAll();

    Optional<FinancialLedgerAggregate> findById(UUID id);

    FinancialLedgerAggregate save(FinancialLedgerAggregate financialLedgerAggregate);

    void deleteById(UUID id);

    Optional<FinancialLedgerAggregate> createByAttributeData(FinancialLedgerAttributeData data);

}
