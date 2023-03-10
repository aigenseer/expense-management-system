package de.dhbw.plugins.rest.mapper.controller.model.user;

import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.rest.controller.user.UserController;
import de.dhbw.plugins.rest.mapper.model.user.preview.UserPreview;
import de.dhbw.plugins.rest.mapper.model.user.preview.UserToUserPreviewModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class UserToUserPreviewMapper implements Function<User, UserPreview> {

    private final UserToUserPreviewModelMapper userToUserPreviewModelMapper;

    @Override
    public UserPreview apply(final User user) {
        return map(user);
    }

    private UserPreview map(final User user) {
        UserPreview userPreview = userToUserPreviewModelMapper.apply(user);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel();
        userPreview.add(selfLink);
        return userPreview;
    }

}
