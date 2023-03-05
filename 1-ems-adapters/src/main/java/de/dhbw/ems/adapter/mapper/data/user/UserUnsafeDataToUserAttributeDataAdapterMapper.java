package de.dhbw.ems.adapter.mapper.data.user;

import de.dhbw.ems.application.domain.service.user.UserAttributeData;

import java.util.function.Function;

public interface UserUnsafeDataToUserAttributeDataAdapterMapper extends Function<IUserUnsafeData, UserAttributeData> {
}
