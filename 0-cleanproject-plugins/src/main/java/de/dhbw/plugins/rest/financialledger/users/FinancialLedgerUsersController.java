package de.dhbw.plugins.rest.financialledger.users;

import de.dhbw.cleanproject.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.model.user.userdata.AppendUserData;
import de.dhbw.cleanproject.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.plugins.mapper.financialledger.FinancialLedgerUserPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.financialledger.user.FinancialLedgerUserController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/user/{userId}/financialledger/{financialLedgerId}/users", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class FinancialLedgerUsersController {

    private final FinancialLedgerService financialLedgerService;
    private final FinancialLedgerUserPreviewCollectionModelFactory financialLedgerUserPreviewCollectionModelFactory;

    @GetMapping
    public ResponseEntity<UserPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerService.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();
        return ResponseEntity.ok(financialLedgerUserPreviewCollectionModelFactory.create(userId, financialLedger));
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody AppendUserData data) {
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerService.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();
        UUID financialLedgerUserId = UUID.fromString(data.getUserId());
        if (!financialLedgerService.appendUser(financialLedgerUserId, financialLedger.getId())) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(FinancialLedgerUserController.class).findOne(financialLedger.getId(), financialLedgerUserId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }



}
