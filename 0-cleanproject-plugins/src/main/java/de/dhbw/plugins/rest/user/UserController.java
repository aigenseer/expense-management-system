package de.dhbw.plugins.rest.user;

import de.dhbw.cleanproject.adapter.user.updatedata.UserUpdateData;
import de.dhbw.cleanproject.adapter.user.updatedata.UserUpdateDataToUserMapper;
import de.dhbw.cleanproject.adapter.user.usermodel.UserModel;
import de.dhbw.cleanproject.application.UserApplicationService;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.plugins.mapper.user.UserToUserModelMapper;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.AllArgsConstructor;
import org.javatuples.Pair;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/user/{userId}", produces = "application/vnd.siren+json")
@AllArgsConstructor

public class UserController {

    private final UserApplicationService userApplicationService;

    private final UserUpdateDataToUserMapper userUpdateDataToUserMapper;
    private final UserOperationService userOperationService;
    private final UserToUserModelMapper userToUserModelMapper;

    @GetMapping("/")
    public ResponseEntity<UserModel> findOne(@PathVariable("userId") UUID id) {
        Optional<User> userOptional = userApplicationService.findById(id);
        if (!userOptional.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        UserModel userModel = userToUserModelMapper.apply(userOptional.get());

        Link selfLink = linkTo(methodOn(UserController.class).findOne(id)).withSelfRel()
                .andAffordance(afford(methodOn(UserController.class).update(id, null)))
                .andAffordance(afford(methodOn(UserController.class).delete(id)));
        userModel.add(selfLink);

        return ResponseEntity.ok(userModel);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@PathVariable("userId") UUID id, @Valid @RequestBody UserUpdateData userUpdateData) {
        Optional<User> userOptional = userApplicationService.findById(id);
        if (!userOptional.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = userUpdateDataToUserMapper.apply(Pair.with(userOptional.get(), userUpdateData));
        userApplicationService.save(user);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(user.getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId) {
        if (!userOperationService.deleteUser(userId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
