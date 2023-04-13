package de.dhbw.plugins.rest.controller.financialledger.users;

import de.dhbw.ems.adapter.application.financialledger.FinancialLedgerApplicationAdapter;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.plugins.rest.controller.booking.users.data.AppendUserData;
import de.dhbw.plugins.rest.controller.financialledger.user.FinancialLedgerUserController;
import de.dhbw.plugins.rest.controller.utils.WebMvcLinkBuilderUtils;
import de.dhbw.plugins.rest.mapper.controller.factory.financialledger.FinancialLedgerUserPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.mapper.model.user.preview.UserPreviewCollectionModel;
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
@RequestMapping(value = "/api/user/{userId}/financialledger/{financialLedgerAggregateId}/users", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class FinancialLedgerUsersController {

    private final FinancialLedgerApplicationAdapter financialLedgerApplicationAdapter;
    private final FinancialLedgerUserPreviewCollectionModelFactory financialLedgerUserPreviewCollectionModelFactory;

    @GetMapping
    public ResponseEntity<UserPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId) {
        Optional<FinancialLedger> optionalFinancialLedgerAggregate = financialLedgerApplicationAdapter.find(userId, financialLedgerAggregateId);
        if (!optionalFinancialLedgerAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedgerAggregate.get();
        return ResponseEntity.ok(financialLedgerUserPreviewCollectionModelFactory.create(userId, financialLedger));
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @Valid @RequestBody AppendUserData data) {
        Optional<FinancialLedger> optionalFinancialLedgerAggregate = financialLedgerApplicationAdapter.find(userId, financialLedgerAggregateId);
        if (!optionalFinancialLedgerAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedgerAggregate.get();
        UUID financialLedgerUserId = UUID.fromString(data.getUserId());
        if (!financialLedgerApplicationAdapter.appendUser(financialLedgerUserId, financialLedger.getId())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(FinancialLedgerUserController.class).findOne(financialLedger.getId(), financialLedgerUserId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }



}
