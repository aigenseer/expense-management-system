package de.dhbw.ems.application.archive.factory;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.domain.financialledger.FinancialLedger;

public interface FinancialLedgerArchiveCreator {

    TmpFile createTmpZipArchive(FinancialLedger financialLedger);

}
