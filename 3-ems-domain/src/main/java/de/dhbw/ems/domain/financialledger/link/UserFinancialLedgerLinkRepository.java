package de.dhbw.ems.domain.financialledger.link;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserFinancialLedgerLinkRepository {

    Optional<UserFinancialLedgerLink> findByIds(UUID userId, UUID financialLedgerId);

    List<UserFinancialLedgerLink> findByUserId(UUID userId);

    List<UserFinancialLedgerLink> findByBookingAggregateId(UUID financialLedgerId);

    UserFinancialLedgerLink create(UUID userId, UUID financialLedgerId);

    void deleteById(UUID userId, UUID financialLedgerId);

    boolean exists(UUID userId, UUID financialLedgerId);
}
