package de.dhbw.ems.adapter.model.financialledger.preview;

import de.dhbw.ems.domain.financialledger.FinancialLedger;

import java.util.function.Function;

public interface FinancialLedgerToFinancialLedgerPreviewModelAdapterMapper extends Function<FinancialLedger, FinancialLedgerPreviewModel> {
}
