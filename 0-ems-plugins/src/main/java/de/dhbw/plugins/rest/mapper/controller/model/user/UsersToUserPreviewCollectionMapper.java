package de.dhbw.plugins.rest.mapper.controller.model.user;

import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.rest.mapper.model.user.preview.UserPreview;
import de.dhbw.plugins.rest.mapper.model.user.preview.UserPreviewCollectionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class UsersToUserPreviewCollectionMapper implements Function<Iterable<User>, UserPreviewCollectionModel> {

    private final UserToUserPreviewMapper userToUserPreviewMapper;

    @Override
    public UserPreviewCollectionModel apply(final Iterable<User> users) {
        return map(users);
    }

    private UserPreviewCollectionModel map(final Iterable<User> users) {
        List<UserPreview> userPreviewModels = StreamSupport.stream(users.spliterator(), false)
                .map(userToUserPreviewMapper)
                .collect(Collectors.toList());
        return new UserPreviewCollectionModel(userPreviewModels);
    }

}
