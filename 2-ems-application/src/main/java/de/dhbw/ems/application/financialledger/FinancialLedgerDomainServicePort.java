package de.dhbw.ems.application.financialledger;

import de.dhbw.ems.domain.financialledger.FinancialLedger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerDomainServicePort {

    Optional<FinancialLedger> updateByAttributeData(FinancialLedger financialLedger, FinancialLedgerAttributeData data);

}
