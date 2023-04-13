package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.application.domain.service.financialledger.entity.FinancialLedgerDomainService;
import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.application.domain.service.financialledger.link.UserFinancialLedgerLinkDomainService;
import de.dhbw.ems.application.domain.service.user.UserDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.mediator.colleage.FinancialLedgerColleague;
import de.dhbw.ems.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregate;
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
    public Optional<FinancialLedgerAggregate> create(UUID userId, FinancialLedgerAttributeData attributeData) {
        Optional<User> userOptional = userDomainService.findById(userId);
        if (userOptional.isPresent()) {
            Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerDomainService.createByAttributeData(attributeData);
            if (optionalFinancialLedgerAggregate.isPresent()) {
                getMediator().onLinkUserToFinancialLedger(userOptional.get(), optionalFinancialLedgerAggregate.get(), this);
                onLinkUserToFinancialLedger(userOptional.get(), optionalFinancialLedgerAggregate.get());
                return optionalFinancialLedgerAggregate;
            }
        }
        return Optional.empty();
    }

    public Optional<FinancialLedgerAggregate> find(UUID userId, UUID financialLedgerAggregateId) {
        Optional<UserFinancialLedgerLink> userFinancialLedgerLink = userFinancialLedgerLinkDomainService.findById(userId, financialLedgerAggregateId);
        if (userFinancialLedgerLink.isPresent()) {
            return Optional.of(userFinancialLedgerLink.get().getFinancialLedgerAggregate());
        }
        return Optional.empty();
    }

    @Transactional
    public boolean unlinkUser(UUID userId, UUID financialLedgerAggregateId) {
        Optional<User> optionalUser = userDomainService.findById(userId);
        if (optionalUser.isPresent()) {
            Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerDomainService.findById(financialLedgerAggregateId);
            if (optionalFinancialLedgerAggregate.isPresent()) {
                User user = optionalUser.get();
                FinancialLedgerAggregate financialLedgerAggregate = optionalFinancialLedgerAggregate.get();
                if (financialLedgerAggregate.getAuthorizedUser().contains(user)) {
                    getMediator().onUnlinkUserToFinancialLedger(user, financialLedgerAggregate, this);
                    onUnlinkUserToFinancialLedger(user, financialLedgerAggregate);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasUserPermission(UUID userId, UUID financialLedgerAggregateId) {
        return find(userId, financialLedgerAggregateId).isPresent();
    }

    @Transactional
    public boolean appendUser(UUID userId, UUID financialLedgerAggregateId) {
        Optional<User> userOptional = userDomainService.findById(userId);
        if (userOptional.isPresent()) {
            Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerDomainService.findById(financialLedgerAggregateId);
            if (optionalFinancialLedgerAggregate.isPresent()) {
                getMediator().onLinkUserToFinancialLedger(userOptional.get(), optionalFinancialLedgerAggregate.get(), this);
                onLinkUserToFinancialLedger(userOptional.get(), optionalFinancialLedgerAggregate.get());
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean delete(UUID userId, UUID financialLedgerAggregateId) {
        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = find(userId, financialLedgerAggregateId);
        if (optionalFinancialLedgerAggregate.isPresent()) {
            onDeleteFinancialLedger(optionalFinancialLedgerAggregate.get());
            return true;
        }
        return false;
    }

}
