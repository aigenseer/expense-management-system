package de.dhbw.plugins.persistence.hibernate.financialledger.aggregate;

import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataFinancialLedgerAggregateRepository extends JpaRepository<FinancialLedgerAggregate, UUID> {

}
