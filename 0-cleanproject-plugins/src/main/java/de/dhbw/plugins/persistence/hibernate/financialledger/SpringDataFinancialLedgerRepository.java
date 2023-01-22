package de.dhbw.plugins.persistence.hibernate.financialledger;

import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataFinancialLedgerRepository extends JpaRepository<FinancialLedger, UUID> {

}
