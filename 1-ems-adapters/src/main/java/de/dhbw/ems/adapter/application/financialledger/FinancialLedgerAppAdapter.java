package de.dhbw.ems.adapter.application.financialledger;

import de.dhbw.ems.application.financialledger.FinancialLedgerAttributeData;
import de.dhbw.ems.application.mediator.service.impl.FinancialLedgerServicePort;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FinancialLedgerAppAdapter implements FinancialLedgerApplicationAdapter {

    private final FinancialLedgerServicePort financialLedgerServicePort;


    @Override
    public Optional<FinancialLedger> create(UUID userId, FinancialLedgerAttributeData attributeData) {
        return financialLedgerServicePort.create(userId, attributeData);
    }

    @Override
    public Optional<FinancialLedger> find(UUID id, UUID financialLedgerId) {
        return financialLedgerServicePort.find(id, financialLedgerId);
    }

    @Override
    public boolean unlinkUser(UUID id, UUID financialLedgerId) {
        return financialLedgerServicePort.unlinkUser(id, financialLedgerId);
    }

    @Override
    public boolean hasUserPermission(UUID id, UUID financialLedgerId) {
        return financialLedgerServicePort.unlinkUser(id, financialLedgerId);
    }

    @Override
    public boolean appendUser(UUID id, UUID financialLedgerId) {
        return financialLedgerServicePort.unlinkUser(id, financialLedgerId);
    }

    @Override
    public boolean delete(UUID id, UUID financialLedgerId) {
        return financialLedgerServicePort.delete(id, financialLedgerId);
    }
}