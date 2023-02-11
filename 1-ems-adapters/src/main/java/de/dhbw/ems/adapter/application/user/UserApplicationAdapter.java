package de.dhbw.ems.adapter.application.user;

import de.dhbw.ems.application.user.UserAttributeData;
import de.dhbw.ems.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserApplicationAdapter {

    boolean delete(UUID id);

    List<User> findAll();

    Optional<User> findById(UUID id);

    Optional<User> createByAttributeData(UserAttributeData userData);

    Optional<User> updateByAttributeDataWithId(UUID id, UserAttributeData userData);

}
