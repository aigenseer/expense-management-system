package de.dhbw.ems.adapter.application.financialledger;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerApplicationAdapter {


    Optional<FinancialLedgerAggregate> create(UUID userId, FinancialLedgerAttributeData attributeData);

    Optional<FinancialLedgerAggregate> find(UUID id, UUID financialLedgerId);

    boolean unlinkUser(UUID id, UUID financialLedgerId);

    boolean hasUserPermission(UUID id, UUID financialLedgerId);

    boolean appendUser(UUID id, UUID financialLedgerId);

    boolean delete(UUID id, UUID financialLedgerId);

    Optional<FinancialLedgerAggregate> updateByAttributeData(FinancialLedgerAggregate financialLedgerAggregate, FinancialLedgerAttributeData data);

    TmpFile createTmpZipArchive(FinancialLedgerAggregate financialLedgerAggregate);

    List<FinancialLedgerAggregate> findFinancialLedgerAggregatesByUserId(UUID userId);

}
