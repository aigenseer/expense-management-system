package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.application.financialledger.aggregate.FinancialLedgerAggregateDomainService;
import de.dhbw.ems.application.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.application.financialledger.entity.FinancialLedgerDomainService;
import de.dhbw.ems.application.financialledger.link.UserFinancialLedgerLinkDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.mediator.colleage.FinancialLedgerColleague;
import de.dhbw.ems.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.ems.application.user.UserDomainService;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerLink;
import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FinancialLedgerOperationService extends FinancialLedgerColleague implements FinancialLedgerService {

    private final UserDomainService userDomainService;
    private final FinancialLedgerAggregateDomainService financialLedgerAggregateDomainService;
    private final UserFinancialLedgerLinkDomainService userFinancialLedgerLinkDomainService;

    public FinancialLedgerOperationService(
            final ConcreteApplicationMediator mediator,
            final UserDomainService userDomainService,
            final FinancialLedgerAggregateDomainService financialLedgerAggregateDomainService,
            final FinancialLedgerDomainService financialLedgerDomainService,
            final UserFinancialLedgerLinkDomainService userFinancialLedgerLinkDomainService
    ) {
        super(mediator, financialLedgerAggregateDomainService, financialLedgerDomainService, userFinancialLedgerLinkDomainService);
        this.userDomainService = userDomainService;
        this.financialLedgerAggregateDomainService = financialLedgerAggregateDomainService;
        this.userFinancialLedgerLinkDomainService = userFinancialLedgerLinkDomainService;
    }

    public Optional<FinancialLedgerAggregate> create(UUID userId, FinancialLedgerAttributeData attributeData) {
        Optional<User> userOptional = userDomainService.findById(userId);
        if (userOptional.isPresent()) {
            Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerAggregateDomainService.createByAttributeData(attributeData);
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

    public boolean unlinkUser(UUID userId, UUID financialLedgerAggregateId) {
        Optional<User> optionalUser = userDomainService.findById(userId);
        if (optionalUser.isPresent()) {
            Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerAggregateDomainService.findById(financialLedgerAggregateId);
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

    public boolean appendUser(UUID userId, UUID financialLedgerAggregateId) {
        Optional<User> userOptional = userDomainService.findById(userId);
        if (userOptional.isPresent()) {
            Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerAggregateDomainService.findById(financialLedgerAggregateId);
            if (optionalFinancialLedgerAggregate.isPresent()) {
                getMediator().onLinkUserToFinancialLedger(userOptional.get(), optionalFinancialLedgerAggregate.get(), this);
                onLinkUserToFinancialLedger(userOptional.get(), optionalFinancialLedgerAggregate.get());
                return true;
            }
        }
        return false;
    }

    public boolean delete(UUID userId, UUID financialLedgerAggregateId) {
        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = find(userId, financialLedgerAggregateId);
        if (optionalFinancialLedgerAggregate.isPresent()) {
            getMediator().onDeleteFinancialLedger(optionalFinancialLedgerAggregate.get(), this);
            onDeleteFinancialLedger(optionalFinancialLedgerAggregate.get());
            return true;
        }
        return false;
    }

}
