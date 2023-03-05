package de.dhbw.plugins.rest.mapper.model.financialledger.preview;

import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;

import java.util.function.Function;

public interface FinancialLedgerAggregateToFinancialLedgerPreviewModelMapper extends Function<FinancialLedgerAggregate, FinancialLedgerPreviewModel> {
}
