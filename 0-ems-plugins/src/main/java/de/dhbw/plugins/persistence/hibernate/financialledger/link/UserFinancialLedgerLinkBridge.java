package de.dhbw.plugins.persistence.hibernate.financialledger.link;

import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerId;
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

    private UserFinancialLedgerId createUserFinancialLedgerId(UUID userId, UUID userFinancialLedgerId){
        return UserFinancialLedgerId.builder().userId(userId).financialLedgerId(userFinancialLedgerId).build();
    }

    @Override
    public Optional<UserFinancialLedgerLink> findByIds(UUID userId, UUID financialLedgerId) {
        return repository.findById(createUserFinancialLedgerId(userId, financialLedgerId));
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
    public UserFinancialLedgerLink create(UUID userId, UUID financialLedgerId) {
        UserFinancialLedgerLink userFinancialLedgerLink = UserFinancialLedgerLink.builder()
                .id(createUserFinancialLedgerId(userId, financialLedgerId))
                .build();
        return repository.save(userFinancialLedgerLink);
    }

    @Override
    public void deleteById(UUID userId, UUID financialLedgerId) {
        repository.deleteById(UserFinancialLedgerId.builder().userId(userId).financialLedgerId(financialLedgerId).build());
    }

    @Override
    public boolean exists(UUID userId, UUID financialLedgerId) {
        return repository.existsById(UserFinancialLedgerId.builder().userId(userId).financialLedgerId(financialLedgerId).build());
    }

}