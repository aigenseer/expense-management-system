package de.dhbw.ems.application.archive.mapper.financialledger;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.domain.financialledger.FinancialLedger;

import java.util.function.Function;

public interface FinancialLedgerToCSVFileMapperFunction extends Function<FinancialLedger, TmpFile> {

}