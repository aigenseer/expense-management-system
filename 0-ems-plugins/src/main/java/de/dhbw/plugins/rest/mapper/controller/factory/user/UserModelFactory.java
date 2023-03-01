package de.dhbw.plugins.rest.mapper.controller.factory.user;

import de.dhbw.plugins.rest.mapper.model.user.model.UserModel;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.rest.mapper.controller.model.user.UserToUserModelMapper;
import de.dhbw.plugins.rest.controller.user.UserController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class UserModelFactory {

    private final UserToUserModelMapper userToUserModelMapper;

    public UserModel create(User user){
        UserModel userModel = userToUserModelMapper.apply(user);

        Link selfLink = linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel()
                .andAffordance(afford(methodOn(UserController.class).update(user.getId(), null)))
                .andAffordance(afford(methodOn(UserController.class).delete(user.getId())));
        userModel.add(selfLink);

        return userModel;
    }


}
