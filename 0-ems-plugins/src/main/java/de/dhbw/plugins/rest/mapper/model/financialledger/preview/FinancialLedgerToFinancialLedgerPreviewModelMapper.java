package de.dhbw.plugins.rest.mapper.model.financialledger.preview;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;

import java.util.function.Function;

public interface FinancialLedgerToFinancialLedgerPreviewModelMapper extends Function<FinancialLedger, FinancialLedgerPreviewModel> {
}
