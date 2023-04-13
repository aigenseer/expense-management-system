package de.dhbw.ems.adapter.application.financialledger;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.service.FinancialLedgerArchiveFactoryDomainServicePort;
import de.dhbw.ems.application.domain.service.financialledger.entity.FinancialLedgerDomainServicePort;
import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerData;
import de.dhbw.ems.application.domain.service.financialledger.link.UserFinancialLedgerLinkDomainServicePort;
import de.dhbw.ems.application.mediator.service.impl.FinancialLedgerServicePort;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FinancialLedgerAppAdapter implements FinancialLedgerApplicationAdapter {

    private final FinancialLedgerServicePort financialLedgerServicePort;
    private final FinancialLedgerDomainServicePort financialLedgerDomainServicePort;
    private final FinancialLedgerArchiveFactoryDomainServicePort financialLedgerArchiveFactoryDomainServicePort;
    private final UserFinancialLedgerLinkDomainServicePort userFinancialLedgerLinkDomainServicePort;


    @Override
    public Optional<FinancialLedger> create(UUID userId, FinancialLedgerData attributeData) {
        return financialLedgerServicePort.create(userId, attributeData);
    }

    @Override
    public Optional<FinancialLedger> find(UUID id, UUID financialLedgerId) {
        return financialLedgerServicePort.find(id, financialLedgerId);
    }

    @Override
    public boolean unlinkUser(UUID id, UUID financialLedgerId) {
        return financialLedgerServicePort.unlinkUser(id, financialLedgerId);
    }

    @Override
    public boolean hasUserPermission(UUID id, UUID financialLedgerId) {
        return financialLedgerServicePort.hasUserPermission(id, financialLedgerId);
    }

    @Override
    public boolean appendUser(UUID id, UUID financialLedgerId) {
        return financialLedgerServicePort.appendUser(id, financialLedgerId);
    }

    @Override
    public boolean delete(UUID id, UUID financialLedgerId) {
        return financialLedgerServicePort.delete(id, financialLedgerId);
    }

    @Override
    public Optional<FinancialLedger> updateByAttributeData(FinancialLedger financialLedger, FinancialLedgerData data) {
        return financialLedgerDomainServicePort.updateByAttributeData(financialLedger, data);
    }

    @Override
    public TmpFile createTmpZipArchive(FinancialLedger financialLedger) {
        return financialLedgerArchiveFactoryDomainServicePort.createTmpZipArchive(financialLedger);
    }

    @Override
    public List<FinancialLedger> findFinancialLedgersByUserId(UUID userId) {
        return userFinancialLedgerLinkDomainServicePort.findFinancialLedgersByUserId(userId);
    }

}
