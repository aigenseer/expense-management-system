package de.dhbw.plugins.rest.financialledger.users;

import de.dhbw.cleanproject.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.model.user.userdata.AppendUserData;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.plugins.mapper.user.UsersToUserPreviewCollectionMapper;
import de.dhbw.plugins.rest.financialledger.user.FinancialLedgerUserController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = "/api/user/{userId}/financialledger/{financialLedgerId}/users", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class FinancialLedgerUsersController {

    private final UserOperationService userOperationService;
    private final UsersToUserPreviewCollectionMapper usersToUserPreviewCollectionMapper;

    @GetMapping
    public ResponseEntity<UserPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();

        UserPreviewCollectionModel previewCollectionModel = usersToUserPreviewCollectionMapper.apply(financialLedger.getAuthorizedUser());
        previewCollectionModel.getContent().forEach(userPreview -> {
            Link selfLink = linkTo(methodOn(FinancialLedgerUserController.class).findOne(financialLedgerId, userPreview.getId())).withSelfRel();
            userPreview.removeLinks();
            userPreview.add(selfLink);
        });

        Link selfLink = linkTo(methodOn(this.getClass()).listAll(userId, financialLedgerId)).withSelfRel()
                .andAffordance(afford(methodOn(this.getClass()).create(userId, financialLedgerId, null)));
        previewCollectionModel.add(selfLink);

        return ResponseEntity.ok(previewCollectionModel);
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody AppendUserData data) {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();
        UUID financialLedgerUserId = UUID.fromString(data.getUserId());
        if (!userOperationService.appendUserToFinancialLedger(financialLedgerUserId, financialLedger.getId())) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(FinancialLedgerUserController.class).findOne(financialLedger.getId(), financialLedgerUserId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }



}
