package de.dhbw.plugins.rest.controller.users;

import de.dhbw.ems.adapter.application.user.UserApplicationAdapter;
import de.dhbw.ems.adapter.mapper.data.user.UserUnsafeDataToUserAttributeDataAdapterMapper;
import de.dhbw.ems.application.user.UserAttributeData;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.rest.controller.user.UserController;
import de.dhbw.plugins.rest.controller.user.data.UserData;
import de.dhbw.plugins.rest.controller.utils.WebMvcLinkBuilderUtils;
import de.dhbw.plugins.rest.mapper.controller.factory.user.UserPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.mapper.model.user.preview.UserPreviewCollectionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/users", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class UsersController {

    private final UserApplicationAdapter userApplicationAdapter;
    private final UserUnsafeDataToUserAttributeDataAdapterMapper userDataToUserMapper;
    private final UserPreviewCollectionModelFactory userPreviewCollectionModelFactory;

    @GetMapping("/")
    public ResponseEntity<UserPreviewCollectionModel> listAll() {
        return ResponseEntity.ok(userPreviewCollectionModelFactory.create(userApplicationAdapter.findAll()));
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@Valid @RequestBody UserData userData) {
        UserAttributeData userAttributeData = userDataToUserMapper.apply(userData);
        Optional<User> optionalUser = userApplicationAdapter.createByAttributeData(userAttributeData);
        if (!optionalUser.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(optionalUser.get().getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
