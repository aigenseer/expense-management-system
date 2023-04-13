package de.dhbw.plugins.persistence.hibernate.financialledger.link;

import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerId;
import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SpringDataUserFinancialLedgerLinkRepository extends JpaRepository<UserFinancialLedgerLink, UserFinancialLedgerId> {

    @Query("SELECT l FROM UserFinancialLedgerLink l WHERE l.id.userId = ?1")
    List<UserFinancialLedgerLink> findByUserId(UUID userId);

    @Query("SELECT l FROM UserFinancialLedgerLink l WHERE l.id.financialLedgerId = ?1")
    List<UserFinancialLedgerLink> findByBookingAggregateId(UUID financialLedgerId);

}
