package de.dhbw.ems.application.user;

import de.dhbw.ems.domain.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserDomainService extends UserDomainServicePort {

    User save(User user);

    void deleteById(UUID id);

    Optional<User> updateByAttributeData(User user, UserAttributeData userData);

}
