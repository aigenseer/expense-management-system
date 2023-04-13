package de.dhbw.plugins.rest.controller.financialledgers;

import de.dhbw.ems.adapter.application.financialledger.FinancialLedgerApplicationAdapter;
import de.dhbw.ems.adapter.application.user.UserApplicationAdapter;
import de.dhbw.ems.adapter.mapper.data.financialledger.FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper;
import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerData;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.rest.controller.financialledger.FinancialLedgerController;
import de.dhbw.plugins.rest.controller.utils.WebMvcLinkBuilderUtils;
import de.dhbw.plugins.rest.mapper.controller.factory.financialledger.FinancialLedgerPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.mapper.model.financialledger.preview.FinancialLedgerPreviewCollectionModel;
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
@RequestMapping(value = "/api/user/{userId}/financialledger/", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class FinancialLedgersController {

    private final UserApplicationAdapter userApplicationAdapter;
    private final FinancialLedgerApplicationAdapter financialLedgerApplicationAdapter;
    private final FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper dataAdapterMapper;
    private final FinancialLedgerPreviewCollectionModelFactory financialLedgerPreviewCollectionModelFactory;

    @GetMapping
    public ResponseEntity<FinancialLedgerPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId) {
        Optional<User> userOptional = userApplicationAdapter.findById(userId);
        if (!userOptional.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        FinancialLedgerPreviewCollectionModel previewCollectionModel = financialLedgerPreviewCollectionModelFactory.create(userId);

        return ResponseEntity.ok(previewCollectionModel);
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @Valid @RequestBody de.dhbw.plugins.rest.controller.financialledgers.data.FinancialLedgerData data) {
        FinancialLedgerData financialLedgerData = dataAdapterMapper.apply(data);
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationAdapter.create(userId, financialLedgerData);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(FinancialLedgerController.class).findOne(userId, financialLedger.getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
