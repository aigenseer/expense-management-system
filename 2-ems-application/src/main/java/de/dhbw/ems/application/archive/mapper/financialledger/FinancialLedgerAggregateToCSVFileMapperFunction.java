package de.dhbw.ems.application.archive.mapper.financialledger;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregate;

import java.util.function.Function;

public interface FinancialLedgerAggregateToCSVFileMapperFunction extends Function<FinancialLedgerAggregate, TmpFile> {

}