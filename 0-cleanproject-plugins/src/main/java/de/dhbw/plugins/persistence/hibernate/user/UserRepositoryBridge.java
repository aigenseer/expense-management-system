package de.dhbw.plugins.persistence.hibernate.user;

import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.cleanproject.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserRepositoryBridge implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    @Override
    public List<User> findAllUsers() {
        return springDataUserRepository.findAll();
    }


    @Override
    public User saveUser(User user) {
        return springDataUserRepository.save(user);
    }

}
