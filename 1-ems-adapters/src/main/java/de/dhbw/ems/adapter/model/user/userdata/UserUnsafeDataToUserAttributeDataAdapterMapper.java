package de.dhbw.ems.adapter.model.user.userdata;

import de.dhbw.ems.application.user.UserAttributeData;

import java.util.function.Function;

public interface UserUnsafeDataToUserAttributeDataAdapterMapper extends Function<IUserUnsafeData, UserAttributeData> {
}
