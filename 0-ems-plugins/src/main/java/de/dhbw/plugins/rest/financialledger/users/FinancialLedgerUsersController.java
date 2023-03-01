package de.dhbw.plugins.rest.financialledger.users;

import de.dhbw.ems.adapter.application.financialledger.FinancialLedgerApplicationAdapter;
import de.dhbw.ems.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.ems.adapter.model.user.userdata.AppendUserData;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
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
@RequestMapping(value = "/api/user/{userId}/financialledger/{financialLedgerAggregateId}/users", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class FinancialLedgerUsersController {

    private final FinancialLedgerApplicationAdapter financialLedgerApplicationAdapter;
    private final FinancialLedgerUserPreviewCollectionModelFactory financialLedgerUserPreviewCollectionModelFactory;

    @GetMapping
    public ResponseEntity<UserPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId) {
        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerApplicationAdapter.find(userId, financialLedgerAggregateId);
        if (!optionalFinancialLedgerAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedgerAggregate financialLedgerAggregate = optionalFinancialLedgerAggregate.get();
        return ResponseEntity.ok(financialLedgerUserPreviewCollectionModelFactory.create(userId, financialLedgerAggregate));
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @Valid @RequestBody AppendUserData data) {
        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerApplicationAdapter.find(userId, financialLedgerAggregateId);
        if (!optionalFinancialLedgerAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedgerAggregate financialLedgerAggregate = optionalFinancialLedgerAggregate.get();
        UUID financialLedgerUserId = UUID.fromString(data.getUserId());
        if (!financialLedgerApplicationAdapter.appendUser(financialLedgerUserId, financialLedgerAggregate.getId())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(FinancialLedgerUserController.class).findOne(financialLedgerAggregate.getId(), financialLedgerUserId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }



}
