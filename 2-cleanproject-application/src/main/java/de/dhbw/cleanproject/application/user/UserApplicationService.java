package de.dhbw.cleanproject.application.user;

import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.cleanproject.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<User> findAllById(Iterable<UUID> ids){
        return repository.findAllById(ids);
    }

    public Optional<User> create(UserAttributeData userData){
        User user = User.builder().id(UUID.randomUUID()).build();
        return update(user, userData);
    }

    public Optional<User> update(UUID id, UserAttributeData userData){
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isPresent()){
            return update(optionalUser.get(), userData);
        }
        return Optional.empty();
    }

    public Optional<User> update(User user, UserAttributeData userData){
        if (userData.getName() != null){
            user.setName(userData.getName());
        }
        if (userData.getEmail() != null){
            user.setEmail(userData.getEmail());
        }
        if (userData.getPhoneNumber() != null){
            user.setPhoneNumber(userData.getPhoneNumber());
        }
        return Optional.of(save(user));
    }


}