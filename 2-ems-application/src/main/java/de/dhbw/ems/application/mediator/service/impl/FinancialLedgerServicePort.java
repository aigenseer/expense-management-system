package de.dhbw.ems.application.mediator.service.impl;

import de.dhbw.ems.application.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;

import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerServicePort {

    Optional<FinancialLedgerAggregate> create(UUID userId, FinancialLedgerAttributeData attributeData);

    Optional<FinancialLedgerAggregate> find(UUID id, UUID financialLedgerId);

    boolean unlinkUser(UUID id, UUID financialLedgerId);

    boolean delete(UUID id, UUID financialLedgerId);

    boolean appendUser(UUID id, UUID financialLedgerId);

    boolean hasUserPermission(UUID id, UUID financialLedgerId);

}
