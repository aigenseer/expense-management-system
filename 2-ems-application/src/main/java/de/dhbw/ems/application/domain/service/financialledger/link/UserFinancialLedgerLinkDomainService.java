package de.dhbw.ems.application.domain.service.financialledger.link;

import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerLink;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserFinancialLedgerLinkDomainService extends UserFinancialLedgerLinkDomainServicePort {

    Optional<UserFinancialLedgerLink> findById(UUID userId, UUID financialLedgerId);

    Optional<UserFinancialLedgerLink> create(UUID userId, UUID financialLedgerId);

    List<UserFinancialLedgerLink> findByUserId(UUID userId);

    List<UserFinancialLedgerLink> findByBookingAggregateId(UUID financialLedgerId);

    void deleteById(UUID userId, UUID financialLedgerId);

    boolean exists(UUID userId, UUID financialLedgerId);

}
