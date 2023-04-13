package de.dhbw.plugins.persistence.hibernate.financialledger.link;

import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerAggregateId;
import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerLink;
import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class UserFinancialLedgerLinkBridge implements UserFinancialLedgerLinkRepository {

    private final SpringDataUserFinancialLedgerLinkRepository repository;

    private UserFinancialLedgerAggregateId createUserFinancialLedgerAggregateId(UUID userId, UUID userFinancialLedgerAggregateId){
        return UserFinancialLedgerAggregateId.builder().userId(userId).financialLedgerId(userFinancialLedgerAggregateId).build();
    }

    @Override
    public Optional<UserFinancialLedgerLink> findByIds(UUID userId, UUID userFinancialLedgerAggregateId) {
        return repository.findById(createUserFinancialLedgerAggregateId(userId, userFinancialLedgerAggregateId));
    }

    @Override
    public List<UserFinancialLedgerLink> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<UserFinancialLedgerLink> findByBookingAggregateId(UUID userFinancialLedgerLinkId) {
        return repository.findByBookingAggregateId(userFinancialLedgerLinkId);
    }

    @Override
    public UserFinancialLedgerLink create(UUID userId, UUID userFinancialLedgerAggregateId) {
        UserFinancialLedgerLink userFinancialLedgerLink = UserFinancialLedgerLink.builder()
                .id(createUserFinancialLedgerAggregateId(userId, userFinancialLedgerAggregateId))
                .build();
        return repository.save(userFinancialLedgerLink);
    }

    @Override
    public void deleteById(UUID userId, UUID userFinancialLedgerAggregateId) {
        repository.deleteById(UserFinancialLedgerAggregateId.builder().userId(userId).financialLedgerId(userFinancialLedgerAggregateId).build());
    }

    @Override
    public boolean exists(UUID userId, UUID userFinancialLedgerAggregateId) {
        return repository.existsById(UserFinancialLedgerAggregateId.builder().userId(userId).financialLedgerId(userFinancialLedgerAggregateId).build());
    }

}