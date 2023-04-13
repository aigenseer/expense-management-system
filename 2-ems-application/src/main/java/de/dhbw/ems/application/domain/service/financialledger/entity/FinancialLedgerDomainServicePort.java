package de.dhbw.ems.application.domain.service.financialledger.entity;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;

import java.util.Optional;

public interface FinancialLedgerDomainServicePort {

    Optional<FinancialLedgerAggregate> updateByAttributeData(FinancialLedgerAggregate financialLedgerAggregate, FinancialLedgerAttributeData data);

}
