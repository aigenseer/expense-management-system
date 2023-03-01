package de.dhbw.plugins.mapper.user;

import de.dhbw.ems.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.rest.controller.users.UsersController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class UserPreviewCollectionModelFactory {

    private final UsersToUserPreviewCollectionMapper usersToUserPreviewCollectionMapper;

    public UserPreviewCollectionModel create(Iterable<User> users){
        UserPreviewCollectionModel userPreviewCollectionModel = usersToUserPreviewCollectionMapper.apply(users);

        Link selfLink = linkTo(methodOn(UsersController.class).listAll()).withSelfRel()
                .andAffordance(afford(methodOn(UsersController.class).create(null)));
        userPreviewCollectionModel.add(selfLink);
        return userPreviewCollectionModel;
    }
}
