package de.dhbw.cleanproject.application.user;

import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.cleanproject.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
