package de.dhbw.cleanproject.application.book;

import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.cleanproject.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public User saveUsers(User user) {
        return userRepository.saveUser(user);
    }

}
