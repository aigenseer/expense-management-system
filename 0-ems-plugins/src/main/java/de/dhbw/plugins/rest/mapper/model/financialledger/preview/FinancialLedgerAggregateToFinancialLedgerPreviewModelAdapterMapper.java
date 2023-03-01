package de.dhbw.plugins.rest.mapper.model.financialledger.preview;

import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;

import java.util.function.Function;

public interface FinancialLedgerAggregateToFinancialLedgerPreviewModelAdapterMapper extends Function<FinancialLedgerAggregate, FinancialLedgerPreviewModel> {
}
