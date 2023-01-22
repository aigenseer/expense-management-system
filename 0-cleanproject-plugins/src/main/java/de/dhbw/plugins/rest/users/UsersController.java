package de.dhbw.plugins.rest.users;

import de.dhbw.cleanproject.adapter.user.preview.UserPreview;
import de.dhbw.cleanproject.adapter.user.preview.UserPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.user.preview.UserToUserPreviewModelMapper;
import de.dhbw.cleanproject.adapter.user.userdata.UserData;
import de.dhbw.cleanproject.adapter.user.userdata.UserDataToUserMapper;
import de.dhbw.cleanproject.application.book.UserApplicationService;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.plugins.rest.user.UserController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/users", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class UsersController {

    private final UserApplicationService userApplicationService;
    private final UserToUserPreviewModelMapper userToUserPreviewModelMapper;
    private final UserDataToUserMapper userDataToUserMapper;

    @GetMapping("/")
    public ResponseEntity<UserPreviewCollectionModel> listAll() {
        List<UserPreview> userPreviewModels = userApplicationService.findAll().stream()
                .map(user -> {
                    UserPreview userPreview = userToUserPreviewModelMapper.apply(user);
                    Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel();
                    userPreview.add(selfLink);
                    return userPreview;
                })
                .collect(Collectors.toList());

        UserPreviewCollectionModel userPreviewCollectionModel = new UserPreviewCollectionModel(userPreviewModels);

        Link selfLink = linkTo(methodOn(UsersController.class).listAll()).withSelfRel()
                .andAffordance(afford(methodOn(UsersController.class).create(null)));
        userPreviewCollectionModel.add(selfLink);

        return ResponseEntity.ok(userPreviewCollectionModel);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@Valid @RequestBody UserData userData) {
        User user = userDataToUserMapper.apply(userData);
        userApplicationService.save(user);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(user.getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
