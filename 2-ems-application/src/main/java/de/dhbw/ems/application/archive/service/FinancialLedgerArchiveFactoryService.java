package de.dhbw.ems.application.archive.service;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.factory.FinancialLedgerArchiveCreator;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancialLedgerArchiveFactoryService implements FinancialLedgerArchiveFactoryDomainServicePort {

    private final FinancialLedgerArchiveCreator financialLedgerArchiveCreator;

    @Override
    public TmpFile createTmpZipArchive(FinancialLedgerAggregate financialLedgerAggregate) {
        return financialLedgerArchiveCreator.createTmpZipArchive(financialLedgerAggregate);
    }
}
