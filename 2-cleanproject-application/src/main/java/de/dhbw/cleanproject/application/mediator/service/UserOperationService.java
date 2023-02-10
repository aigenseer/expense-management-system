package de.dhbw.cleanproject.application.mediator.service;

import de.dhbw.cleanproject.application.mediator.ConcreteApplicationMediator;
import de.dhbw.cleanproject.application.mediator.colleage.UserColleague;
import de.dhbw.cleanproject.application.mediator.service.impl.UserService;
import de.dhbw.cleanproject.application.user.UserApplicationService;
import de.dhbw.cleanproject.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserOperationService extends UserColleague implements UserService {

    private final ConcreteApplicationMediator mediator;
    private final UserApplicationService userApplicationService;

    public UserOperationService(ConcreteApplicationMediator mediator, UserApplicationService userApplicationService) {
        super(mediator, userApplicationService);
        this.mediator = mediator;
        this.userApplicationService = userApplicationService;
    }

    public boolean delete(UUID id){
        Optional<User> optionalUser = userApplicationService.findById(id);
        if (optionalUser.isPresent()){
            mediator.onDeleteUser(optionalUser.get(), this);
            onDeleteUser(optionalUser.get());
            return true;
        }
        return false;
    }

}
