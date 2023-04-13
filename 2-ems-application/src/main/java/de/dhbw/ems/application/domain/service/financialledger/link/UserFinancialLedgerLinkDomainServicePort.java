package de.dhbw.ems.application.domain.service.financialledger.link;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregate;

import java.util.List;
import java.util.UUID;

public interface UserFinancialLedgerLinkDomainServicePort {

    List<FinancialLedgerAggregate> findFinancialLedgerAggregatesByUserId(UUID userId);

}
