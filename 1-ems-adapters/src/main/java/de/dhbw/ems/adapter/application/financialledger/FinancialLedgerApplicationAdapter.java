package de.dhbw.ems.adapter.application.financialledger;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerData;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerApplicationAdapter {


    Optional<FinancialLedger> create(UUID userId, FinancialLedgerData attributeData);

    Optional<FinancialLedger> find(UUID id, UUID financialLedgerId);

    boolean unlinkUser(UUID id, UUID financialLedgerId);

    boolean hasUserPermission(UUID id, UUID financialLedgerId);

    boolean appendUser(UUID id, UUID financialLedgerId);

    boolean delete(UUID id, UUID financialLedgerId);

    Optional<FinancialLedger> updateByAttributeData(FinancialLedger financialLedger, FinancialLedgerData data);

    TmpFile createTmpZipArchive(FinancialLedger financialLedger);

    List<FinancialLedger> findFinancialLedgersByUserId(UUID userId);

}
