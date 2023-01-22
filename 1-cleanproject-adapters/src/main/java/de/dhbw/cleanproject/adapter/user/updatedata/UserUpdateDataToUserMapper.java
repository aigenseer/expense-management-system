package de.dhbw.cleanproject.adapter.user.updatedata;


import de.dhbw.cleanproject.adapter.user.userdata.UserDataToUserMapper;
import de.dhbw.cleanproject.domain.user.User;

import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserUpdateDataToUserMapper implements Function<Pair<User, UserUpdateData>, User> {

    private final UserDataToUserMapper userDataToUserMapper;

    @Override
    public User apply(final Pair<User, UserUpdateData> pair) {
        return map(pair);
    }

    private User map(final Pair<User, UserUpdateData> pair) {
        User.UserBuilder builder = User.builder();
        User user = pair.getValue0();
        User updateUser = userDataToUserMapper.apply(pair.getValue1());

        builder.id(user.getId());
        builder.financialLedgers(user.getFinancialLedgers());
        if (updateUser.getName() != null){
             builder.name(updateUser.getName());
        }else if (user.getName() != null){
            builder.name(user.getName());
        }

        if (updateUser.getEmail() != null){
            builder.email(updateUser.getEmail());
        }else if (user.getEmail() != null){
            builder.email(user.getEmail());
        }

        if (updateUser.getPhoneNumber() != null){
            builder.phoneNumber(updateUser.getPhoneNumber());
        }else if (user.getPhoneNumber() != null){
            builder.phoneNumber(user.getPhoneNumber());
        }

        return builder.build();
    }

}
