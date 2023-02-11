package de.dhbw.ems.adapter.model.financialledger.model;

import de.dhbw.ems.domain.financialledger.FinancialLedger;

import java.util.function.Function;

public interface FinancialLedgerToFinancialLedgerModelAdapterMapper extends Function<FinancialLedger, FinancialLedgerModel> {
}
