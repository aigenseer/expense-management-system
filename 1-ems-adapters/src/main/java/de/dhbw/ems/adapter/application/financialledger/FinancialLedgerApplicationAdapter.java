package de.dhbw.ems.adapter.application.financialledger;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerApplicationAdapter {


    Optional<FinancialLedger> create(UUID userId, FinancialLedgerAttributeData attributeData);

    Optional<FinancialLedger> find(UUID id, UUID financialLedgerAggregateId);

    boolean unlinkUser(UUID id, UUID financialLedgerAggregateId);

    boolean hasUserPermission(UUID id, UUID financialLedgerAggregateId);

    boolean appendUser(UUID id, UUID financialLedgerAggregateId);

    boolean delete(UUID id, UUID financialLedgerAggregateId);

    Optional<FinancialLedger> updateByAttributeData(FinancialLedger financialLedger, FinancialLedgerAttributeData data);

    TmpFile createTmpZipArchive(FinancialLedger financialLedger);

    List<FinancialLedger> findFinancialLedgerAggregatesByUserId(UUID userId);

}
