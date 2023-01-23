package de.dhbw.cleanproject.application;

import de.dhbw.cleanproject.domain.financialledger.FinancialLedgerRepository;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.cleanproject.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserRepository repository;
    private final FinancialLedgerRepository financialLedgerRepository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<User> findAllById(Iterable<UUID> ids){
        return repository.findAllById(ids);
    }



}
