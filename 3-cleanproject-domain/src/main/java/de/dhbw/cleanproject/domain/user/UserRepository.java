package de.dhbw.cleanproject.domain.user;

import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(UUID id);

    User save(User user);

    void deleteById(UUID id);

    List<User> findAllById(Iterable<UUID> ids);
}
