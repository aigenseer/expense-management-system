package de.dhbw.plugins.rest.financialledger.users;

import de.dhbw.cleanproject.adapter.financialledger.data.FinancialLedgerUserAppendData;
import de.dhbw.cleanproject.adapter.user.preview.UserPreview;
import de.dhbw.cleanproject.adapter.user.preview.UserPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.user.preview.UserToUserPreviewModelMapper;
import de.dhbw.cleanproject.application.book.UserApplicationService;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.plugins.rest.financialledger.user.FinancialLedgerUserController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/user/{userId}/financialledger/{financialLedgerId}/users", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class FinancialLedgersUsersController {

    private final UserApplicationService userApplicationService;
    private final UserToUserPreviewModelMapper userToUserPreviewModelMapper;

    @GetMapping
    public ResponseEntity<UserPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = userApplicationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();

        List<UserPreview> userPreviewModels = financialLedger.getAuthorizedUser().stream().map(user -> {
            UserPreview preview = userToUserPreviewModelMapper.apply(user);
            Link selfLink = linkTo(methodOn(FinancialLedgerUserController.class).findOne(user.getId(), financialLedgerId)).withSelfRel();
            preview.add(selfLink);
            return preview;
        }).collect(Collectors.toList());
        UserPreviewCollectionModel previewCollectionModel = new UserPreviewCollectionModel(userPreviewModels);

        Link selfLink = linkTo(methodOn(this.getClass()).listAll(userId, financialLedgerId)).withSelfRel()
                .andAffordance(afford(methodOn(this.getClass()).create(userId, financialLedgerId, null)));
        previewCollectionModel.add(selfLink);

        return ResponseEntity.ok(previewCollectionModel);
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody FinancialLedgerUserAppendData data) {
        Optional<FinancialLedger> optionalFinancialLedger = userApplicationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();
        UUID financialLedgerUserId = UUID.fromString(data.getUserId());
        if (!userApplicationService.appendUserToFinancialLedger(financialLedgerUserId, financialLedger.getId())) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(FinancialLedgerUserController.class).findOne(financialLedger.getId(), financialLedgerUserId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }



}
