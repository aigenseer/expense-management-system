package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.domain.service.financialledger.entity.FinancialLedgerDomainService;
import de.dhbw.ems.application.domain.service.financialledger.link.UserFinancialLedgerLinkDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.ems.domain.user.User;

public class FinancialLedgerColleague extends Colleague {

    private final FinancialLedgerDomainService financialLedgerDomainService;
    private final UserFinancialLedgerLinkDomainService userFinancialLedgerLinkDomainService;

    public FinancialLedgerColleague(
            final ConcreteApplicationMediator mediator,
            final FinancialLedgerDomainService financialLedgerDomainService,
            final UserFinancialLedgerLinkDomainService userFinancialLedgerLinkDomainService
    ) {
        super(mediator);
        this.financialLedgerDomainService = financialLedgerDomainService;
        this.userFinancialLedgerLinkDomainService = userFinancialLedgerLinkDomainService;
    }

    @Override
    public void onLinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {
        userFinancialLedgerLinkDomainService.create(user.getId(), financialLedger.getId());
    }

    @Override
    public void onUnlinkUserToFinancialLedger(User user, FinancialLedger financialLedger) {
        if (userFinancialLedgerLinkDomainService.exists(user.getId(), financialLedger.getId())){
            financialLedger.getUserFinancialLedgerLinks().removeIf(link -> link.getId().getUserId().equals(user.getId()));
            userFinancialLedgerLinkDomainService.deleteById(user.getId(), financialLedger.getId());
        }
        if (financialLedger.getUserFinancialLedgerLinks().size() == 0){
            onDeleteFinancialLedger(financialLedger);
        }
    }

    @Override
    public void onDeleteUser(User user) {
        userFinancialLedgerLinkDomainService.findByUserId(user.getId()).forEach(r -> onUnlinkUserToFinancialLedger(r.getUser(), r.getFinancialLedger()));
    }

    public void onDeleteFinancialLedger(FinancialLedger financialLedger) {
        financialLedgerDomainService.deleteById(financialLedger.getId());
    }

}
