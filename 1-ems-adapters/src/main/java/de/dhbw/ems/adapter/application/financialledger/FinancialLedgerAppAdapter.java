package de.dhbw.ems.adapter.application.financialledger;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.service.FinancialLedgerArchiveFactoryDomainServicePort;
import de.dhbw.ems.application.domain.service.financialledger.aggregate.FinancialLedgerDomainServicePort;
import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.application.domain.service.financialledger.link.UserFinancialLedgerLinkDomainServicePort;
import de.dhbw.ems.application.mediator.service.impl.FinancialLedgerServicePort;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
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
    public Optional<FinancialLedgerAggregate> create(UUID userId, FinancialLedgerAttributeData attributeData) {
        return financialLedgerServicePort.create(userId, attributeData);
    }

    @Override
    public Optional<FinancialLedgerAggregate> find(UUID id, UUID financialLedgerAggregateId) {
        return financialLedgerServicePort.find(id, financialLedgerAggregateId);
    }

    @Override
    public boolean unlinkUser(UUID id, UUID financialLedgerAggregateId) {
        return financialLedgerServicePort.unlinkUser(id, financialLedgerAggregateId);
    }

    @Override
    public boolean hasUserPermission(UUID id, UUID financialLedgerAggregateId) {
        return financialLedgerServicePort.hasUserPermission(id, financialLedgerAggregateId);
    }

    @Override
    public boolean appendUser(UUID id, UUID financialLedgerAggregateId) {
        return financialLedgerServicePort.appendUser(id, financialLedgerAggregateId);
    }

    @Override
    public boolean delete(UUID id, UUID financialLedgerAggregateId) {
        return financialLedgerServicePort.delete(id, financialLedgerAggregateId);
    }

    @Override
    public Optional<FinancialLedgerAggregate> updateByAttributeData(FinancialLedgerAggregate financialLedgerAggregate, FinancialLedgerAttributeData data) {
        return financialLedgerDomainServicePort.updateByAttributeData(financialLedgerAggregate, data);
    }

    @Override
    public TmpFile createTmpZipArchive(FinancialLedgerAggregate financialLedgerAggregate) {
        return financialLedgerArchiveFactoryDomainServicePort.createTmpZipArchive(financialLedgerAggregate);
    }

    @Override
    public List<FinancialLedgerAggregate> findFinancialLedgerAggregatesByUserId(UUID userId) {
        return userFinancialLedgerLinkDomainServicePort.findFinancialLedgerAggregatesByUserId(userId);
    }

}
