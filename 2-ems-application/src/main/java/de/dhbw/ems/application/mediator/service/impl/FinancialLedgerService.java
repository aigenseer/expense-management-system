package de.dhbw.ems.application.mediator.service.impl;

import java.util.UUID;

public interface FinancialLedgerService extends FinancialLedgerServicePort {

    boolean hasUserPermission(UUID id, UUID financialLedgerId);

    boolean appendUser(UUID id, UUID financialLedgerId);

}
