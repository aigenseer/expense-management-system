package de.dhbw.plugins.rest.financialledger.user;

import de.dhbw.cleanproject.adapter.user.preview.UserPreview;
import de.dhbw.cleanproject.adapter.user.preview.UserToUserPreviewModelMapper;
import de.dhbw.cleanproject.application.UserApplicationService;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/financialledger/{financialLedgerId}/user/{financialLedgerUserId}", produces = "application/vnd.siren+json")
@AllArgsConstructor

public class FinancialLedgerUserController {

    private final UserApplicationService userApplicationService;
    private final UserOperationService userOperationService;
    private final UserToUserPreviewModelMapper userToUserPreviewModelMapper;

    @GetMapping
    public ResponseEntity<UserPreview> findOne(@PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("financialLedgerUserId") UUID financialLedgerUserId) {
        Optional<User> optionalUser = userApplicationService.findById(financialLedgerUserId);
        if (!optionalUser.isPresent() || !userOperationService.hasUserPermissionToFinancialLedger(financialLedgerUserId, financialLedgerId))
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = optionalUser.get();
        UserPreview userPreview = userToUserPreviewModelMapper.apply(user);

        Link selfLink = linkTo(methodOn(this.getClass()).findOne(financialLedgerId, financialLedgerUserId)).withSelfRel()
                .andAffordance(afford(methodOn(this.getClass()).delete(financialLedgerId, financialLedgerUserId)));
        userPreview.add(selfLink);

        return ResponseEntity.ok(userPreview);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("financialLedgerId") UUID financialLedgerId, @PathVariable("financialLedgerUserId") UUID financialLedgerUserId) {
        return null;
    }

}
