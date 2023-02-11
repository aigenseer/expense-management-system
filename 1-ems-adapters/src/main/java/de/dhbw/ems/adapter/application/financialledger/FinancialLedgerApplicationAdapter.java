package de.dhbw.ems.adapter.application.financialledger;

import de.dhbw.ems.application.financialledger.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.FinancialLedger;

import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerApplicationAdapter {

    Optional<FinancialLedger> create(UUID userId, FinancialLedgerAttributeData attributeData);

    Optional<FinancialLedger> find(UUID id, UUID financialLedgerId);

    boolean unlinkUser(UUID id, UUID financialLedgerId);

    boolean hasUserPermission(UUID id, UUID financialLedgerId);

    boolean appendUser(UUID id, UUID financialLedgerId);

    boolean delete(UUID id, UUID financialLedgerId);

}
