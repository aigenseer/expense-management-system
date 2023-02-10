package de.dhbw.plugins.rest.user;

import de.dhbw.ems.adapter.model.user.updatedata.UserUpdateData;
import de.dhbw.ems.adapter.model.user.userdata.UserUnsafeDataToUserAttributeDataAdapterMapper;
import de.dhbw.ems.adapter.model.user.usermodel.UserModel;
import de.dhbw.ems.application.mediator.service.impl.UserService;
import de.dhbw.ems.application.user.UserAttributeData;
import de.dhbw.ems.application.user.UserDomainService;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.mapper.user.UserModelFactory;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/user/{userId}", produces = "application/vnd.siren+json")
@AllArgsConstructor

public class UserController {

    private final UserDomainService userDomainService;
    private final UserUnsafeDataToUserAttributeDataAdapterMapper userUnsafeDataToUserAttributeDataAdapterMapper;
    private final UserService userService;
    private final UserModelFactory userModelFactory;

    @GetMapping("/")
    public ResponseEntity<UserModel> findOne(@PathVariable("userId") UUID id) {
        Optional<User> userOptional = userDomainService.findById(id);
        if (!userOptional.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(userModelFactory.create(userOptional.get()));
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@PathVariable("userId") UUID id, @Valid @RequestBody UserUpdateData userUpdateData) {
        Optional<User> userOptional = userDomainService.findById(id);
        if (!userOptional.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        UserAttributeData userAttributeData = userUnsafeDataToUserAttributeDataAdapterMapper.apply(userUpdateData);
        Optional<User> optionalUser = userDomainService.updateByAttributeDataWithId(id, userAttributeData);
        if (!optionalUser.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(optionalUser.get().getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId) {
        if (!userService.delete(userId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
