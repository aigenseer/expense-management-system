package de.dhbw.cleanproject.domain.user;

import java.util.List;

public interface UserRepository {

    List<User> findAllUsers();

    User saveUser(User user);
}
