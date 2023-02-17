package de.dhbw.ems.application.user;

import de.dhbw.ems.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDomainServicePort {

    List<User> findAll();

    Optional<User> findById(UUID id);

    Optional<User> createByAttributeData(UserAttributeData userData);

    Optional<User> updateByAttributeDataWithId(UUID id, UserAttributeData userData);

}
