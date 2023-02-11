package de.dhbw.plugins.rest.financialledgers;

import de.dhbw.ems.adapter.application.user.UserApplicationAdapter;
import de.dhbw.ems.adapter.model.financialledger.data.FinancialLedgerData;
import de.dhbw.ems.adapter.model.financialledger.data.FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper;
import de.dhbw.ems.adapter.model.financialledger.preview.FinancialLedgerPreviewCollectionModel;
import de.dhbw.ems.application.financialledger.FinancialLedgerAttributeData;
import de.dhbw.ems.application.mediator.service.impl.FinancialLedgerService;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;
import de.dhbw.plugins.mapper.financialledger.FinancialLedgerPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.financialledger.FinancialLedgerController;
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
@RequestMapping(value = "/api/user/{userId}/financialledger/", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class FinancialLedgersController {

    private final UserApplicationAdapter userApplicationAdapter;
    private final FinancialLedgerService financialLedgerService;
    private final FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper dataAdapterMapper;
    private final FinancialLedgerPreviewCollectionModelFactory financialLedgerPreviewCollectionModelFactory;

    @GetMapping
    public ResponseEntity<FinancialLedgerPreviewCollectionModel> listAll(@PathVariable("userId") UUID userId) {
        Optional<User> userOptional = userApplicationAdapter.findById(userId);
        if (!userOptional.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        User user = userOptional.get();

        FinancialLedgerPreviewCollectionModel previewCollectionModel = financialLedgerPreviewCollectionModelFactory.create(userId, user.getFinancialLedgers());

        return ResponseEntity.ok(previewCollectionModel);
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("userId") UUID userId, @Valid @RequestBody FinancialLedgerData data) {
        FinancialLedgerAttributeData financialLedgerAttributeData = dataAdapterMapper.apply(data);
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerService.create(userId, financialLedgerAttributeData);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(FinancialLedgerController.class).findOne(userId, financialLedger.getId()));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

}
