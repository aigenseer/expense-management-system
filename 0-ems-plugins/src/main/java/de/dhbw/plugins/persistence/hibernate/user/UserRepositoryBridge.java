package de.dhbw.plugins.persistence.hibernate.user;

import de.dhbw.ems.domain.user.User;
import de.dhbw.ems.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class UserRepositoryBridge implements UserRepository {

    private final SpringDataUserRepository springDataRepository;

    @Override
    public List<User> findAll() {
        return springDataRepository.findAll();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return springDataRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return springDataRepository.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public List<User> findAllById(Iterable<UUID> ids) {
        return springDataRepository.findAllById(ids);
    }

}
