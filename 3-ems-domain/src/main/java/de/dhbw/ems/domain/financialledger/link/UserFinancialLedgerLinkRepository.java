package de.dhbw.ems.domain.financialledger.link;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserFinancialLedgerLinkRepository {

    Optional<UserFinancialLedgerLink> findByIds(UUID userId, UUID financialLedgerAggregateId);

    List<UserFinancialLedgerLink> findByUserId(UUID userId);

    List<UserFinancialLedgerLink> findByBookingAggregateId(UUID financialLedgerAggregateId);

    UserFinancialLedgerLink create(UUID userId, UUID financialLedgerAggregateId);

    void deleteById(UUID userId, UUID financialLedgerAggregateId);

    boolean exists(UUID userId, UUID financialLedgerAggregateId);
}
