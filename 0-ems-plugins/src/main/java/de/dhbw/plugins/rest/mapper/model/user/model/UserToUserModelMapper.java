package de.dhbw.plugins.rest.mapper.model.user.model;

import de.dhbw.ems.domain.user.User;

import java.util.function.Function;

public interface UserToUserModelMapper extends Function<User, UserModel> {
}
