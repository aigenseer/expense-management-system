package de.dhbw.plugins.mapper.user.model;

import de.dhbw.ems.adapter.model.user.preview.UserPreview;
import de.dhbw.ems.adapter.model.user.preview.UserToUserPreviewModelAdapterMapper;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.rest.controller.user.UserController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class UserToUserPreviewMapper implements Function<User, UserPreview> {

    private final UserToUserPreviewModelAdapterMapper userToUserPreviewModelAdapterMapper;

    @Override
    public UserPreview apply(final User user) {
        return map(user);
    }

    private UserPreview map(final User user) {
        UserPreview userPreview = userToUserPreviewModelAdapterMapper.apply(user);
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel();
        userPreview.add(selfLink);
        return userPreview;
    }

}
