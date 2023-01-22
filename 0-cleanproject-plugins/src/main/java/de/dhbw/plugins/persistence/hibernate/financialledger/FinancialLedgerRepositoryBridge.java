package de.dhbw.plugins.persistence.hibernate.financialledger;

import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class FinancialLedgerRepositoryBridge implements FinancialLedgerRepository {

    private final SpringDataFinancialLedgerRepository springDataFinancialLedgerRepository;

    @Override
    public List<FinancialLedger> findAll() {
        return springDataFinancialLedgerRepository.findAll();
    }

    @Override
    public Optional<FinancialLedger> findById(UUID id) {
        return springDataFinancialLedgerRepository.findById(id);
    }

    @Override
    public FinancialLedger save(FinancialLedger user) {
        return springDataFinancialLedgerRepository.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        springDataFinancialLedgerRepository.deleteById(id);
    }


}
