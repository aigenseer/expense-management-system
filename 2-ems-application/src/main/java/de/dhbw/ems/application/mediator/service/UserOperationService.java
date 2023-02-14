package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.mediator.colleage.UserColleague;
import de.dhbw.ems.application.mediator.service.impl.UserServicePort;
import de.dhbw.ems.application.user.UserDomainService;
import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserOperationService extends UserColleague implements UserServicePort {

    private final UserDomainService userDomainService;

    public UserOperationService(ConcreteApplicationMediator mediator, UserDomainService userDomainService) {
        super(mediator, userDomainService);
        this.userDomainService = userDomainService;
    }

    public boolean delete(UUID id){
        Optional<User> optionalUser = userDomainService.findById(id);
        if (optionalUser.isPresent()){
            getMediator().onDeleteUser(optionalUser.get(), this);
            onDeleteUser(optionalUser.get());
            return true;
        }
        return false;
    }

}
