package de.dhbw.plugins.rest.user;

import de.dhbw.cleanproject.domain.user.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> findOne(@PathVariable("id") UUID id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<User>> update(@PathVariable("id") UUID id, @RequestBody User user) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        return null;
    }

}
