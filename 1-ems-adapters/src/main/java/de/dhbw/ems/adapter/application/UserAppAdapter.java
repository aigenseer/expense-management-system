package de.dhbw.ems.adapter.application;

import de.dhbw.ems.application.mediator.service.impl.UserServicePort;
import de.dhbw.ems.application.user.UserAttributeData;
import de.dhbw.ems.application.user.UserDomainServicePort;
import de.dhbw.ems.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAppAdapter implements UserApplicationAdapter {

    private final UserDomainServicePort userDomainServicePort;
    private final UserServicePort userServicePort;

    @Override
    public boolean delete(UUID id) {
        return userServicePort.delete(id);
    }

    @Override
    public List<User> findAll() {
        return userDomainServicePort.findAll();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userDomainServicePort.findById(id);
    }

    @Override
    public Optional<User> createByAttributeData(UserAttributeData userData) {
        return userDomainServicePort.createByAttributeData(userData);
    }

    @Override
    public Optional<User> updateByAttributeDataWithId(UUID id, UserAttributeData userData) {
        return userDomainServicePort.updateByAttributeDataWithId(id, userData);
    }

}
