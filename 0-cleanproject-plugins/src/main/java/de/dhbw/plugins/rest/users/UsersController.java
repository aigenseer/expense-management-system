package de.dhbw.plugins.rest.users;

import de.dhbw.cleanproject.adapter.user.preview.UserPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.user.userdata.UserData;
import de.dhbw.cleanproject.adapter.user.userdata.UserUnsafeDataToUserAttributeDataAdapterMapper;
import de.dhbw.cleanproject.application.user.UserApplicationService;
import de.dhbw.cleanproject.application.user.UserAttributeData;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.plugins.mapper.user.UsersToUserPreviewCollectionMapper;
import de.dhbw.plugins.rest.user.UserController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/users", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class UsersController {

    private final UserApplicationService userApplicationService;
    private final UserUnsafeDataToUserAttributeDataAdapterMapper userDataToUserMapper;
    private final UsersToUserPreviewCollectionMapper usersToUserPreviewCollectionMapper;

    @GetMapping("/")
    public ResponseEntity<UserPreviewCollectionModel> listAll() {
        UserPreviewCollectionModel model = usersToUserPreviewCollectionMapper.apply(userApplicationService.findAll());

        Link selfLink = linkTo(methodOn(UsersController.class).listAll()).withSelfRel()
                .andAffordance(afford(methodOn(UsersController.class).create(null)));
        model.add(selfLink);

        return ResponseEntity.ok(model);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@Valid @RequestBody UserData userData) {
        UserAttributeData userAttributeData = userDataToUserMapper.apply(userData);
        Optional<User> optionalUser = userApplicationService.createByAttributeData(userAttributeData);
        if (!optionalUser.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(optionalUser.get().getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
