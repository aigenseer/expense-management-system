package de.dhbw.ems.adapter.application.financialledger;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerApplicationAdapter {


    Optional<FinancialLedgerAggregate> create(UUID userId, FinancialLedgerAttributeData attributeData);

    Optional<FinancialLedgerAggregate> find(UUID id, UUID financialLedgerAggregateId);

    boolean unlinkUser(UUID id, UUID financialLedgerAggregateId);

    boolean hasUserPermission(UUID id, UUID financialLedgerAggregateId);

    boolean appendUser(UUID id, UUID financialLedgerAggregateId);

    boolean delete(UUID id, UUID financialLedgerAggregateId);

    Optional<FinancialLedgerAggregate> updateByAttributeData(FinancialLedgerAggregate financialLedgerAggregate, FinancialLedgerAttributeData data);

    TmpFile createTmpZipArchive(FinancialLedgerAggregate financialLedgerAggregate);

    List<FinancialLedgerAggregate> findFinancialLedgerAggregatesByUserId(UUID userId);

}
