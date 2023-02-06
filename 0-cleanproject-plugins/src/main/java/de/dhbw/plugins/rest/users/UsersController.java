package de.dhbw.plugins.rest.users;

import de.dhbw.cleanproject.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.model.user.userdata.UserData;
import de.dhbw.cleanproject.adapter.model.user.userdata.UserUnsafeDataToUserAttributeDataAdapterMapper;
import de.dhbw.cleanproject.application.user.UserDomainService;
import de.dhbw.cleanproject.application.user.UserAttributeData;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.plugins.mapper.user.UserPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.user.UserController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
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

    private final UserDomainService userDomainService;
    private final UserUnsafeDataToUserAttributeDataAdapterMapper userDataToUserMapper;
    private final UserPreviewCollectionModelFactory userPreviewCollectionModelFactory;

    @GetMapping("/")
    public ResponseEntity<UserPreviewCollectionModel> listAll() {
        return ResponseEntity.ok(userPreviewCollectionModelFactory.create(userDomainService.findAll()));
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@Valid @RequestBody UserData userData) {
        UserAttributeData userAttributeData = userDataToUserMapper.apply(userData);
        Optional<User> optionalUser = userDomainService.createByAttributeData(userAttributeData);
        if (!optionalUser.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(optionalUser.get().getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
