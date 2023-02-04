package de.dhbw.plugins.rest.financialledgers;

import de.dhbw.cleanproject.adapter.financialledger.data.FinancialLedgerData;
import de.dhbw.cleanproject.adapter.financialledger.data.FinancialLedgerDataToFinancialLedgerMapper;
import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerPreviewCollectionModel;
import de.dhbw.cleanproject.application.user.UserApplicationService;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.plugins.mapper.financialledger.FinancialLedgersToFinancialLedgerPreviewCollectionMapper;
import de.dhbw.plugins.rest.financialledger.FinancialLedgerController;
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
@RequestMapping(value = "/api/user/{userId}/financialledger/", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class FinancialLedgersController {

    private final UserApplicationService userApplicationService;
    private final UserOperationService userOperationService;
    private final FinancialLedgerDataToFinancialLedgerMapper financialLedgerMapper;
    private final FinancialLedgersToFinancialLedgerPreviewCollectionMapper financialLedgersToFinancialLedgerPreviewCollectionMapper;

    @GetMapping
    public ResponseEntity<FinancialLedgerPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId) {
        Optional<User> userOptional = userApplicationService.findById(userId);
        if (!userOptional.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        User user = userOptional.get();

        FinancialLedgerPreviewCollectionModel previewCollectionModel = financialLedgersToFinancialLedgerPreviewCollectionMapper.apply(FinancialLedgersToFinancialLedgerPreviewCollectionMapper.Context
                .builder()
                .userId(userId)
                .financialLedgers(user.getFinancialLedgers())
                .build());

        Link selfLink = linkTo(methodOn(this.getClass()).listAll(userId)).withSelfRel()
                .andAffordance(afford(methodOn(this.getClass()).create(userId, null)));
        previewCollectionModel.add(selfLink);

        return ResponseEntity.ok(previewCollectionModel);
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @Valid @RequestBody FinancialLedgerData data) {
        FinancialLedger financialLedger = financialLedgerMapper.apply(data);
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.addFinancialLedgerByUserId(userId, financialLedger);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        financialLedger = optionalFinancialLedger.get();
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(FinancialLedgerController.class).findOne(userId, financialLedger.getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
