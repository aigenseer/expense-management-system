package de.dhbw.cleanproject.adapter.user.preview;

import de.dhbw.cleanproject.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserToUserPreviewModelMapper implements Function<User, UserPreview> {

    @Override
    public UserPreview apply(final User user) {
        return map(user);
    }

    private UserPreview map(final User user) {
        return UserPreview.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail().toString())
                .phoneNumber(user.getPhoneNumber() != null? user.getPhoneNumber().toString(): null)
                .build();
    }
}
