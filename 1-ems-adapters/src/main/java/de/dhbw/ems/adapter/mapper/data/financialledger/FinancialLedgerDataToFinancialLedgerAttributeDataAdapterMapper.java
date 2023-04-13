package de.dhbw.ems.adapter.mapper.data.financialledger;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerData;

import java.util.function.Function;

public interface FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper extends Function<IFinancialLedgerData, FinancialLedgerData> {
}
