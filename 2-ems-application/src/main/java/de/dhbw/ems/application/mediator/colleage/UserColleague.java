package de.dhbw.ems.application.mediator.colleage;

import de.dhbw.ems.application.domain.service.user.UserDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.domain.user.User;

public class UserColleague extends Colleague {

    private final UserDomainService userDomainService;

    public UserColleague(final ConcreteApplicationMediator mediator, final UserDomainService userDomainService) {
        super(mediator);
        this.userDomainService = userDomainService;
    }

    @Override
    public void onDeleteUser(User user) {
        userDomainService.deleteById(user.getId());
    }

}
