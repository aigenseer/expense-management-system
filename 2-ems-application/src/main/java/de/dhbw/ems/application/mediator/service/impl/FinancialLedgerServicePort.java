package de.dhbw.ems.application.mediator.service.impl;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerData;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;

import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerServicePort {

    Optional<FinancialLedger> create(UUID userId, FinancialLedgerData attributeData);

    Optional<FinancialLedger> find(UUID userId, UUID financialLedgerId);

    boolean unlinkUser(UUID userId, UUID financialLedgerId);

    boolean delete(UUID userId, UUID financialLedgerId);

    boolean appendUser(UUID userId, UUID financialLedgerId);

    boolean hasUserPermission(UUID userId, UUID financialLedgerId);

}
