package de.dhbw.ems.adapter.mapper.data.user;

import de.dhbw.ems.abstractioncode.valueobject.email.Email;
import de.dhbw.ems.abstractioncode.valueobject.phonennumber.InternationalPhoneCode;
import de.dhbw.ems.abstractioncode.valueobject.phonennumber.PhoneNumber;
import de.dhbw.ems.application.domain.service.user.UserAttributeData;
import org.springframework.stereotype.Component;

@Component
public class UserUnsafeDataToAttributeDataMapper implements UserUnsafeDataToUserAttributeDataAdapterMapper {

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
