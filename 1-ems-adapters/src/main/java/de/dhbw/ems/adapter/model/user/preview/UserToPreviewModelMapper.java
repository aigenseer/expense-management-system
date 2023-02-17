package de.dhbw.ems.adapter.model.user.preview;

import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserToPreviewModelMapper implements UserToUserPreviewModelAdapterMapper{

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
