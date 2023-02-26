package de.dhbw.plugins.persistence.hibernate.financialledger.entity;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataFinancialLedgerRepository extends JpaRepository<FinancialLedger, UUID> {

}
