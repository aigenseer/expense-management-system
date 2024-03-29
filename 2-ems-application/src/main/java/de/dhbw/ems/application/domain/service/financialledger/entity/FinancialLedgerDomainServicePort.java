package de.dhbw.ems.application.domain.service.financialledger.entity;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerData;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;

import java.util.Optional;

public interface FinancialLedgerDomainServicePort {

    Optional<FinancialLedger> updateByAttributeData(FinancialLedger financialLedger, FinancialLedgerData data);

}
