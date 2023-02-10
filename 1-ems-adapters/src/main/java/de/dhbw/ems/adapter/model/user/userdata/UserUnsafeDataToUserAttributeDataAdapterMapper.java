package de.dhbw.ems.adapter.model.user.userdata;

import de.dhbw.cleanproject.abstractioncode.valueobject.email.Email;
import de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber.InternationalPhoneCode;
import de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber.PhoneNumber;
import de.dhbw.ems.application.user.UserAttributeData;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserUnsafeDataToUserAttributeDataAdapterMapper implements Function<IUserUnsafeData, UserAttributeData> {

    @Override
    public UserAttributeData apply(final IUserUnsafeData userUnsafeData) {
        return map(userUnsafeData);
    }

    private UserAttributeData map(final IUserUnsafeData userUnsafeData) {
        UserAttributeData.UserAttributeDataBuilder builder = UserAttributeData.builder();
        if (userUnsafeData.getName() != null){
            builder.name(userUnsafeData.getName());
        }
        if (userUnsafeData.getEmail() != null){
            builder.email(new Email(userUnsafeData.getEmail()));
        }
        try {
            InternationalPhoneCode internationalPhoneCode = InternationalPhoneCode.valueOf(userUnsafeData.getInternationalPhoneCode());
            if(userUnsafeData.getPhoneNumber() != null){
                PhoneNumber phoneNumber = new PhoneNumber(userUnsafeData.getPhoneNumber(), internationalPhoneCode);
                builder.phoneNumber(phoneNumber);
            }
        }catch (IllegalArgumentException|NullPointerException ignored){}
        return builder.build();
    }

}
