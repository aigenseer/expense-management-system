package de.dhbw.cleanproject.application.mediator.service;

import de.dhbw.cleanproject.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.cleanproject.application.financialledger.FinancialLedgerAttributeData;
import de.dhbw.cleanproject.application.mediator.ConcreteApplicationMediator;
import de.dhbw.cleanproject.application.mediator.colleage.FinancialLedgerColleague;
import de.dhbw.cleanproject.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.cleanproject.application.user.UserApplicationService;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FinancialLedgerOperationService extends FinancialLedgerColleague implements FinancialLedgerService {

    private final ConcreteApplicationMediator mediator;
    private final UserApplicationService userApplicationService;
    private final FinancialLedgerApplicationService financialLedgerApplicationService;

    public FinancialLedgerOperationService(
            final ConcreteApplicationMediator mediator,
            final UserApplicationService userApplicationService,
            final FinancialLedgerApplicationService financialLedgerApplicationService
    ) {
        super(mediator, financialLedgerApplicationService);
        this.mediator = mediator;
        this.userApplicationService = userApplicationService;
        this.financialLedgerApplicationService = financialLedgerApplicationService;
    }

    public Optional<FinancialLedger> create(UUID userId, FinancialLedgerAttributeData attributeData) {
        Optional<User> userOptional = userApplicationService.findById(userId);
        if (userOptional.isPresent()) {
            Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.createByAttributeData(attributeData);
            if (optionalFinancialLedger.isPresent()) {
                mediator.onLinkUserToFinancialLedger(userOptional.get(), optionalFinancialLedger.get(), this);
                return find(userId, optionalFinancialLedger.get().getId());
            }
        }
        return Optional.empty();
    }

    public Optional<FinancialLedger> find(UUID id, UUID financialLedgerId) {
        Optional<User> userOptional = userApplicationService.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get().getFinancialLedgers().stream().filter(f -> f.getId().equals(financialLedgerId)).findFirst();
        }
        return Optional.empty();
    }

    public boolean unlinkUser(UUID id, UUID financialLedgerId) {
        Optional<User> optionalUser = userApplicationService.findById(id);
        if (optionalUser.isPresent()) {
            Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationService.findById(financialLedgerId);
            if (optionalFinancialLedger.isPresent()) {
                User user = optionalUser.get();
                FinancialLedger financialLedger = optionalFinancialLedger.get();
                if (financialLedger.getAuthorizedUser().contains(user) ||
                        user.getFinancialLedgers().contains(financialLedger)
                ) {
                    mediator.onUnlinkUserToFinancialLedger(user, financialLedger, this);
                    onUnlinkUserToFinancialLedger(user, financialLedger);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasUserPermission(UUID id, UUID financialLedgerId) {
        return find(id, financialLedgerId).isPresent();
    }

    public boolean appendUser(UUID id, UUID financialLedgerId) {
        Optional<User> userOptional = userApplicationService.findById(id);
        if (userOptional.isPresent()) {
            Optional<FinancialLedger> financialLedgerOptional = financialLedgerApplicationService.findById(financialLedgerId);
            if (financialLedgerOptional.isPresent()) {
                mediator.onLinkUserToFinancialLedger(userOptional.get(), financialLedgerOptional.get(), this);
                return true;
            }
        }
        return false;
    }

    public boolean delete(UUID id, UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = find(id, financialLedgerId);
        if (optionalFinancialLedger.isPresent()) {
            mediator.onDeleteFinancialLedger(optionalFinancialLedger.get(), this);
            onDeleteFinancialLedger(optionalFinancialLedger.get());
            return true;
        }
        return false;
    }

}