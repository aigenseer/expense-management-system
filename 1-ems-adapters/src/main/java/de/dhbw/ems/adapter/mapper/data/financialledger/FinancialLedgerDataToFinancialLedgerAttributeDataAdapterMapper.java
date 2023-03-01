package de.dhbw.ems.adapter.mapper.data.financialledger;

import de.dhbw.ems.application.financialledger.data.FinancialLedgerAttributeData;

import java.util.function.Function;

public interface FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper extends Function<FinancialLedgerData, FinancialLedgerAttributeData> {
}