package de.dhbw.ems.adapter.model.financialledger.model;

import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;

import java.util.function.Function;

public interface FinancialLedgerAggregateToFinancialLedgerModelAdapterMapper extends Function<FinancialLedgerAggregate, FinancialLedgerModel> {
}
