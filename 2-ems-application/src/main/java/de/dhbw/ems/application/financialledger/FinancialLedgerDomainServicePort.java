package de.dhbw.ems.application.financialledger;

import de.dhbw.ems.domain.financialledger.FinancialLedger;

import java.util.Optional;

public interface FinancialLedgerDomainServicePort {

    Optional<FinancialLedger> updateByAttributeData(FinancialLedger financialLedger, FinancialLedgerAttributeData data);

}
