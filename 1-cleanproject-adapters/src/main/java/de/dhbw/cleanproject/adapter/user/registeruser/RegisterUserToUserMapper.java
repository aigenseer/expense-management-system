package de.dhbw.cleanproject.adapter.user.registeruser;

import de.dhbw.cleanproject.abstractioncode.valueobject.email.Email;
import de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber.InternationalPhoneCode;
import de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber.PhoneNumber;
import de.dhbw.cleanproject.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

@Component
public class RegisterUserToUserMapper implements Function<RegisterUser, User> {

    @Override
    public User apply(final RegisterUser registerUser) {
        return map(registerUser);
    }

    private User map(final RegisterUser registerUser) {
        User.UserBuilder builder = User.builder();
        builder.id(UUID.randomUUID());
        builder.name(registerUser.getName()).email(new Email(registerUser.getEmail()));
        try {
            InternationalPhoneCode internationalPhoneCode = InternationalPhoneCode.valueOf(registerUser.getInternationalPhoneCode());
            if(registerUser.getPhoneNumber() != null){
                PhoneNumber phoneNumber = new PhoneNumber(registerUser.getPhoneNumber(), internationalPhoneCode);
                builder.phoneNumber(phoneNumber);
            }
        }catch (IllegalArgumentException|NullPointerException ignored){}
        return builder.build();
    }

}
