package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.application.domain.service.user.UserDomainService;
import de.dhbw.ems.application.mediator.ConcreteApplicationMediator;
import de.dhbw.ems.application.mediator.colleage.UserColleague;
import de.dhbw.ems.application.mediator.service.impl.UserService;
import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserOperationService extends UserColleague implements UserService {

    private final UserDomainService userDomainService;

    public UserOperationService(ConcreteApplicationMediator mediator, UserDomainService userDomainService) {
        super(mediator, userDomainService);
        this.userDomainService = userDomainService;
    }

    @Transactional
    public boolean delete(UUID userId){
        Optional<User> optionalUser = userDomainService.findById(userId);
        if (optionalUser.isPresent()){
            getMediator().onDeleteUser(optionalUser.get(), this);
            onDeleteUser(optionalUser.get());
            return true;
        }
        return false;
    }

}
