package de.dhbw.ems.adapter.model.financialledger.data;

import de.dhbw.ems.application.financialledger.data.FinancialLedgerAttributeData;

import java.util.function.Function;

public interface FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper extends Function<FinancialLedgerData, FinancialLedgerAttributeData> {
}
