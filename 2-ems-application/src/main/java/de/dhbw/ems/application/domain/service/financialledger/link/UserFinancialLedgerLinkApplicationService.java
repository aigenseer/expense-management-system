package de.dhbw.ems.application.domain.service.financialledger.link;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregate;
import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerLink;
import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFinancialLedgerLinkApplicationService implements UserFinancialLedgerLinkDomainService {

    private final UserFinancialLedgerLinkRepository repository;

    @Override
    public Optional<UserFinancialLedgerLink> findById(UUID userId, UUID financialLedgerAggregateId) {
        return repository.findByIds(userId, financialLedgerAggregateId);
    }

    @Override
    public Optional<UserFinancialLedgerLink> create(UUID userId, UUID financialLedgerAggregateId) {
        return Optional.of(repository.create(userId, financialLedgerAggregateId));
    }

    @Override
    public List<UserFinancialLedgerLink> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<UserFinancialLedgerLink> findByBookingAggregateId(UUID financialLedgerAggregateId) {
        return repository.findByBookingAggregateId(financialLedgerAggregateId);
    }

    @Override
    public void deleteById(UUID userId, UUID financialLedgerAggregateId) {
        repository.deleteById(userId, financialLedgerAggregateId);
    }

    @Override
    public boolean exists(UUID userId, UUID financialLedgerAggregateId) {
        return repository.exists(userId, financialLedgerAggregateId);
    }

    @Override
    public List<FinancialLedgerAggregate> findFinancialLedgerAggregatesByUserId(UUID userId) {
        return findByUserId(userId).stream().map(UserFinancialLedgerLink::getFinancialLedgerAggregate).collect(Collectors.toList());
    }
}
