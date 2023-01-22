package de.dhbw.cleanproject.adapter.user.userdata;

import de.dhbw.cleanproject.abstractioncode.valueobject.email.Email;
import de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber.InternationalPhoneCode;
import de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber.PhoneNumber;
import de.dhbw.cleanproject.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

@Component
public class UserDataToUserMapper implements Function<UserData, User> {

    @Override
    public User apply(final UserData userData) {
        return map(userData);
    }

    private User map(final UserData userData) {
        User.UserBuilder builder = User.builder();
        builder.id(UUID.randomUUID());
        builder.name(userData.getName()).email(new Email(userData.getEmail()));
        try {
            InternationalPhoneCode internationalPhoneCode = InternationalPhoneCode.valueOf(userData.getInternationalPhoneCode());
            if(userData.getPhoneNumber() != null){
                PhoneNumber phoneNumber = new PhoneNumber(userData.getPhoneNumber(), internationalPhoneCode);
                builder.phoneNumber(phoneNumber);
            }
        }catch (IllegalArgumentException|NullPointerException ignored){}
        return builder.build();
    }

}
