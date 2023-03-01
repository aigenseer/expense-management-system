package de.dhbw.ems.adapter.model.financialledger.preview;

import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;

import java.util.function.Function;

public interface FinancialLedgerAggregateToFinancialLedgerPreviewModelAdapterMapper extends Function<FinancialLedgerAggregate, FinancialLedgerPreviewModel> {
}
