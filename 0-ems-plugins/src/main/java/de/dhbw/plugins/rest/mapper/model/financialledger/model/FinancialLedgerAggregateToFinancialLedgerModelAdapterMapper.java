package de.dhbw.plugins.rest.mapper.model.financialledger.model;

import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;

import java.util.function.Function;

public interface FinancialLedgerAggregateToFinancialLedgerModelAdapterMapper extends Function<FinancialLedgerAggregate, FinancialLedgerModel> {
}
