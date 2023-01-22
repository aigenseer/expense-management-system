package de.dhbw.cleanproject.application.book;

import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FinancialLedgerApplicationService {

    private final FinancialLedgerRepository repository;

    public List<FinancialLedger> findAll() {
        return repository.findAll();
    }

    public Optional<FinancialLedger> findById(UUID id) {
        return repository.findById(id);
    }

    public FinancialLedger save(FinancialLedger user) {
        return repository.save(user);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
