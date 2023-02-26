package de.dhbw.ems.application.mediator.service.impl;

import de.dhbw.ems.application.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;

import java.util.Optional;
import java.util.UUID;

public interface FinancialLedgerServicePort {

    Optional<FinancialLedgerAggregate> create(UUID userId, FinancialLedgerAttributeData attributeData);

    Optional<FinancialLedgerAggregate> find(UUID userId, UUID financialLedgerAggregateId);

    boolean unlinkUser(UUID userId, UUID financialLedgerAggregateId);

    boolean delete(UUID userId, UUID financialLedgerAggregateId);

    boolean appendUser(UUID userId, UUID financialLedgerAggregateId);

    boolean hasUserPermission(UUID userId, UUID financialLedgerAggregateId);

}
