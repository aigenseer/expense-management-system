package de.dhbw.ems.application.domain.service.user;

import de.dhbw.ems.domain.user.User;
import de.dhbw.ems.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserApplicationService implements UserDomainService {

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

    public Optional<User> createByAttributeData(UserAttributeData userData){
        User user = User.builder().id(UUID.randomUUID()).name(userData.getName()).email(userData.getEmail()).build();
        return updateByAttributeData(user, userData);
    }

    public Optional<User> updateByAttributeDataWithId(UUID id, UserAttributeData userData){
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isPresent()){
            return updateByAttributeData(optionalUser.get(), userData);
        }
        return Optional.empty();
    }

    public Optional<User> updateByAttributeData(User user, UserAttributeData userData){
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
