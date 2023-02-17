package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.application.financialledger.FinancialLedgerAttributeData;
import de.dhbw.ems.application.financialledger.FinancialLedgerDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.mediator.colleage.FinancialLedgerColleague;
import de.dhbw.ems.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.ems.application.user.UserDomainService;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FinancialLedgerOperationService extends FinancialLedgerColleague implements FinancialLedgerService {

    private final UserDomainService UserDomainService;
    private final FinancialLedgerDomainService financialLedgerDomainService;

    public FinancialLedgerOperationService(
            final ConcreteApplicationMediator mediator,
            final UserDomainService UserDomainService,
            final FinancialLedgerDomainService financialLedgerDomainService
    ) {
        super(mediator, financialLedgerDomainService);
        this.UserDomainService = UserDomainService;
        this.financialLedgerDomainService = financialLedgerDomainService;
    }

    public Optional<FinancialLedger> create(UUID userId, FinancialLedgerAttributeData attributeData) {
        Optional<User> userOptional = UserDomainService.findById(userId);
        if (userOptional.isPresent()) {
            Optional<FinancialLedger> optionalFinancialLedger = financialLedgerDomainService.createByAttributeData(attributeData);
            if (optionalFinancialLedger.isPresent()) {
                getMediator().onLinkUserToFinancialLedger(userOptional.get(), optionalFinancialLedger.get(), this);
                return find(userId, optionalFinancialLedger.get().getId());
            }
        }
        return Optional.empty();
    }

    public Optional<FinancialLedger> find(UUID id, UUID financialLedgerId) {
        Optional<User> userOptional = UserDomainService.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get().getFinancialLedgers().stream().filter(f -> f.getId().equals(financialLedgerId)).findFirst();
        }
        return Optional.empty();
    }

    public boolean unlinkUser(UUID id, UUID financialLedgerId) {
        Optional<User> optionalUser = UserDomainService.findById(id);
        if (optionalUser.isPresent()) {
            Optional<FinancialLedger> optionalFinancialLedger = financialLedgerDomainService.findById(financialLedgerId);
            if (optionalFinancialLedger.isPresent()) {
                User user = optionalUser.get();
                FinancialLedger financialLedger = optionalFinancialLedger.get();
                if (financialLedger.getAuthorizedUser().contains(user) ||
                        user.getFinancialLedgers().contains(financialLedger)
                ) {
                    getMediator().onUnlinkUserToFinancialLedger(user, financialLedger, this);
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
        Optional<User> userOptional = UserDomainService.findById(id);
        if (userOptional.isPresent()) {
            Optional<FinancialLedger> financialLedgerOptional = financialLedgerDomainService.findById(financialLedgerId);
            if (financialLedgerOptional.isPresent()) {
                getMediator().onLinkUserToFinancialLedger(userOptional.get(), financialLedgerOptional.get(), this);
                return true;
            }
        }
        return false;
    }

    public boolean delete(UUID id, UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = find(id, financialLedgerId);
        if (optionalFinancialLedger.isPresent()) {
            getMediator().onDeleteFinancialLedger(optionalFinancialLedger.get(), this);
            onDeleteFinancialLedger(optionalFinancialLedger.get());
            return true;
        }
        return false;
    }

}
