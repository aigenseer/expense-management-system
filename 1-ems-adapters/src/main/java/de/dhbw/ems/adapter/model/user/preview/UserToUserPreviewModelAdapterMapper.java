package de.dhbw.ems.adapter.model.user.preview;

import de.dhbw.ems.domain.user.User;

import java.util.function.Function;

public interface UserToUserPreviewModelAdapterMapper extends Function<User, UserPreview> {
}
