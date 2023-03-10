package de.dhbw.ems.application.archive.factory;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;

public interface FinancialLedgerArchiveCreator {

    TmpFile createTmpZipArchive(FinancialLedgerAggregate financialLedgerAggregate);

}
