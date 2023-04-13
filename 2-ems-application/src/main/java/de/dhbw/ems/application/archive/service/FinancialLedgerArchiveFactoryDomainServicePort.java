package de.dhbw.ems.application.archive.service;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregate;

public interface FinancialLedgerArchiveFactoryDomainServicePort {

    TmpFile createTmpZipArchive(FinancialLedgerAggregate financialLedgerAggregate);

}
