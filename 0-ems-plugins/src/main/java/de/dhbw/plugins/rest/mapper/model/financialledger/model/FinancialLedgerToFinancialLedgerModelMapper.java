package de.dhbw.plugins.rest.mapper.model.financialledger.model;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;

import java.util.function.Function;

public interface FinancialLedgerToFinancialLedgerModelMapper extends Function<FinancialLedger, FinancialLedgerModel> {
}
