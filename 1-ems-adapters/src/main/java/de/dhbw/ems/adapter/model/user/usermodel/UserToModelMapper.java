package de.dhbw.ems.adapter.model.user.usermodel;

import de.dhbw.ems.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserToModelMapper implements UserToUserModelAdapterMapper {

    @Override
    public UserModel apply(final User user) {
        return map(user);
    }

    private UserModel map(final User user) {
        UserModel.UserModelBuilder userModelBuilder = UserModel.builder()
                .name(user.getName())
                .email(user.getEmail().toString())
                .phoneNumber(user.getPhoneNumber() != null? user.getPhoneNumber().toString(): null);
        return userModelBuilder.build();
    }
}
