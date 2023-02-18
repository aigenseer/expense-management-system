package de.dhbw.ems.application.archive.service;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.domain.financialledger.FinancialLedger;

public interface FinancialLedgerArchiveFactoryDomainServicePort {

    TmpFile createTmpZipArchive(FinancialLedger financialLedger);

}
