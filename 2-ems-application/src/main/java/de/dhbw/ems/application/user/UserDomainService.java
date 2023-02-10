package de.dhbw.ems.application.user;

import de.dhbw.ems.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDomainService {

    List<User> findAll();

    Optional<User> findById(UUID id);

    User save(User user);

    void deleteById(UUID id);

    List<User> findAllById(Iterable<UUID> ids);

    Optional<User> createByAttributeData(UserAttributeData userData);

    Optional<User> updateByAttributeDataWithId(UUID id, UserAttributeData userData);

    Optional<User> updateByAttributeData(User user, UserAttributeData userData);


}
