package de.dhbw.ems.application.domain.service.financialledger.link;

import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
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
    public Optional<UserFinancialLedgerLink> findById(UUID userId, UUID financialLedgerId) {
        return repository.findByIds(userId, financialLedgerId);
    }

    @Override
    public Optional<UserFinancialLedgerLink> create(UUID userId, UUID financialLedgerId) {
        return Optional.of(repository.create(userId, financialLedgerId));
    }

    @Override
    public List<UserFinancialLedgerLink> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<UserFinancialLedgerLink> findByBookingAggregateId(UUID financialLedgerId) {
        return repository.findByBookingAggregateId(financialLedgerId);
    }

    @Override
    public void deleteById(UUID userId, UUID financialLedgerId) {
        repository.deleteById(userId, financialLedgerId);
    }

    @Override
    public boolean exists(UUID userId, UUID financialLedgerId) {
        return repository.exists(userId, financialLedgerId);
    }

    @Override
    public List<FinancialLedger> findFinancialLedgersByUserId(UUID userId) {
        return findByUserId(userId).stream().map(UserFinancialLedgerLink::getFinancialLedger).collect(Collectors.toList());
    }
}
