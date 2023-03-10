package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.domain.service.financialledger.aggregate.FinancialLedgerAggregateDomainService;
import de.dhbw.ems.application.domain.service.financialledger.link.UserFinancialLedgerLinkDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.ems.domain.user.User;

public class FinancialLedgerColleague extends Colleague {

    private final FinancialLedgerAggregateDomainService financialLedgerAggregateDomainService;
    private final UserFinancialLedgerLinkDomainService userFinancialLedgerLinkDomainService;

    public FinancialLedgerColleague(
            final ConcreteApplicationMediator mediator,
            final FinancialLedgerAggregateDomainService financialLedgerAggregateDomainService,
            final UserFinancialLedgerLinkDomainService userFinancialLedgerLinkDomainService
    ) {
        super(mediator);
        this.financialLedgerAggregateDomainService = financialLedgerAggregateDomainService;
        this.userFinancialLedgerLinkDomainService = userFinancialLedgerLinkDomainService;
    }

    @Override
    public void onLinkUserToFinancialLedger(User user, FinancialLedgerAggregate financialLedgerAggregate) {
        userFinancialLedgerLinkDomainService.create(user.getId(), financialLedgerAggregate.getId());
    }

    @Override
    public void onUnlinkUserToFinancialLedger(User user, FinancialLedgerAggregate financialLedgerAggregate) {
        if (userFinancialLedgerLinkDomainService.exists(user.getId(), financialLedgerAggregate.getId())){
            financialLedgerAggregate.getUserFinancialLedgerLinks().removeIf(link -> link.getId().getUserId().equals(user.getId()));
            userFinancialLedgerLinkDomainService.deleteById(user.getId(), financialLedgerAggregate.getId());
        }
        if (financialLedgerAggregate.getUserFinancialLedgerLinks().size() == 0){
            onDeleteFinancialLedger(financialLedgerAggregate);
        }
    }

    @Override
    public void onDeleteUser(User user) {
        userFinancialLedgerLinkDomainService.findByUserId(user.getId()).forEach(r -> onUnlinkUserToFinancialLedger(r.getUser(), r.getFinancialLedgerAggregate()));
    }

    public void onDeleteFinancialLedger(FinancialLedgerAggregate financialLedgerAggregate) {
        financialLedgerAggregateDomainService.deleteById(financialLedgerAggregate.getId());
    }

}
