package de.dhbw.plugins.rest.mapper.model.user.preview;

import de.dhbw.ems.domain.user.User;

import java.util.function.Function;

public interface UserToUserPreviewModelMapper extends Function<User, UserPreview> {
}
