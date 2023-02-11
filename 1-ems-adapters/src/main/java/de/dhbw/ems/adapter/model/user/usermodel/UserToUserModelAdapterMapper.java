package de.dhbw.ems.adapter.model.user.usermodel;

import de.dhbw.ems.domain.user.User;

import java.util.function.Function;

public interface UserToUserModelAdapterMapper extends Function<User, UserModel> {
}
