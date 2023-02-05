package de.dhbw.plugins.mapper.user;

import de.dhbw.cleanproject.adapter.model.user.usermodel.UserModel;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.plugins.rest.user.UserController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
