package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.application.domain.service.financialledger.entity.FinancialLedgerDomainService;
import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerData;
import de.dhbw.ems.application.domain.service.financialledger.link.UserFinancialLedgerLinkDomainService;
import de.dhbw.ems.application.domain.service.user.UserDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.mediator.colleage.FinancialLedgerColleague;
import de.dhbw.ems.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerLink;
import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class FinancialLedgerOperationService extends FinancialLedgerColleague implements FinancialLedgerService {

    private final UserDomainService userDomainService;
    private final FinancialLedgerDomainService financialLedgerDomainService;
    private final UserFinancialLedgerLinkDomainService userFinancialLedgerLinkDomainService;

    public FinancialLedgerOperationService(
            final ConcreteApplicationMediator mediator,
            final UserDomainService userDomainService,
            final FinancialLedgerDomainService financialLedgerDomainService,
            final UserFinancialLedgerLinkDomainService userFinancialLedgerLinkDomainService
    ) {
        super(mediator, financialLedgerDomainService, userFinancialLedgerLinkDomainService);
        this.userDomainService = userDomainService;
        this.financialLedgerDomainService = financialLedgerDomainService;
        this.userFinancialLedgerLinkDomainService = userFinancialLedgerLinkDomainService;
    }

    @Transactional
    public Optional<FinancialLedger> create(UUID userId, FinancialLedgerData attributeData) {
        Optional<User> userOptional = userDomainService.findById(userId);
        if (userOptional.isPresent()) {
            Optional<FinancialLedger> optionalFinancialLedger = financialLedgerDomainService.createByAttributeData(attributeData);
            if (optionalFinancialLedger.isPresent()) {
                getMediator().onLinkUserToFinancialLedger(userOptional.get(), optionalFinancialLedger.get(), this);
                onLinkUserToFinancialLedger(userOptional.get(), optionalFinancialLedger.get());
                return optionalFinancialLedger;
            }
        }
        return Optional.empty();
    }

    public Optional<FinancialLedger> find(UUID userId, UUID financialLedgerId) {
        Optional<UserFinancialLedgerLink> userFinancialLedgerLink = userFinancialLedgerLinkDomainService.findById(userId, financialLedgerId);
        if (userFinancialLedgerLink.isPresent()) {
            return Optional.of(userFinancialLedgerLink.get().getFinancialLedger());
        }
        return Optional.empty();
    }

    @Transactional
    public boolean unlinkUser(UUID userId, UUID financialLedgerId) {
        Optional<User> optionalUser = userDomainService.findById(userId);
        if (optionalUser.isPresent()) {
            Optional<FinancialLedger> optionalFinancialLedger = financialLedgerDomainService.findById(financialLedgerId);
            if (optionalFinancialLedger.isPresent()) {
                User user = optionalUser.get();
                FinancialLedger financialLedger = optionalFinancialLedger.get();
                if (financialLedger.getAuthorizedUser().contains(user)) {
                    getMediator().onUnlinkUserToFinancialLedger(user, financialLedger, this);
                    onUnlinkUserToFinancialLedger(user, financialLedger);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasUserPermission(UUID userId, UUID financialLedgerId) {
        return find(userId, financialLedgerId).isPresent();
    }

    @Transactional
    public boolean appendUser(UUID userId, UUID financialLedgerId) {
        Optional<User> userOptional = userDomainService.findById(userId);
        if (userOptional.isPresent()) {
            Optional<FinancialLedger> optionalFinancialLedger = financialLedgerDomainService.findById(financialLedgerId);
            if (optionalFinancialLedger.isPresent()) {
                getMediator().onLinkUserToFinancialLedger(userOptional.get(), optionalFinancialLedger.get(), this);
                onLinkUserToFinancialLedger(userOptional.get(), optionalFinancialLedger.get());
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean delete(UUID userId, UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = find(userId, financialLedgerId);
        if (optionalFinancialLedger.isPresent()) {
            onDeleteFinancialLedger(optionalFinancialLedger.get());
            return true;
        }
        return false;
    }

}
