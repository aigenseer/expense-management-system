package de.dhbw.ems.application.mediator.service.impl;

import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;

import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerServicePort {

    Optional<FinancialLedger> create(UUID userId, FinancialLedgerAttributeData attributeData);

    Optional<FinancialLedger> find(UUID userId, UUID financialLedgerAggregateId);

    boolean unlinkUser(UUID userId, UUID financialLedgerAggregateId);

    boolean delete(UUID userId, UUID financialLedgerAggregateId);

    boolean appendUser(UUID userId, UUID financialLedgerAggregateId);

    boolean hasUserPermission(UUID userId, UUID financialLedgerAggregateId);

}
