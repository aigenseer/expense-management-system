package de.dhbw.plugins.rest.financialledgers;

import de.dhbw.cleanproject.adapter.financialledger.data.FinancialLedgerDataToFinancialLedgerMapper;
import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerPreviewModel;
import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerToFinancialLedgerPreviewModelMapper;
import de.dhbw.cleanproject.application.book.FinancialLedgerApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/user/{userId}/financialledger/", produces = "application/vnd.siren+json")
@RequiredArgsConstructor
public class FinancialLedgerController {

    private final FinancialLedgerApplicationService applicationService;
    private final FinancialLedgerToFinancialLedgerPreviewModelMapper previewModelMapper;
    private final FinancialLedgerDataToFinancialLedgerMapper financialLedgerMapper;

    @GetMapping
    public ResponseEntity<FinancialLedgerPreviewCollectionModel> listAll() {
        List<FinancialLedgerPreviewModel> previewModels = applicationService.findAll().stream()
                .map(user -> {
                    FinancialLedgerPreviewModel preview = previewModelMapper.apply(user);
//                    Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel();
//                    userPreview.add(selfLink);
                    return preview;
                })
                .collect(Collectors.toList());

        FinancialLedgerPreviewCollectionModel previewCollectionModel = new FinancialLedgerPreviewCollectionModel(previewModels);

        Link selfLink = linkTo(methodOn(this.getClass()).listAll()).withSelfRel();
//                .andAffordance(afford(methodOn(this.getClass()).create(null)));
        previewCollectionModel.add(selfLink);

        return ResponseEntity.ok(previewCollectionModel);
    }

//    @PostMapping
//    public ResponseEntity<Void> create(@Valid @RequestBody FinancialLedgerData data) {
//        FinancialLedger financialLedger = financialLedgerMapper.apply(data);
//        applicationService.save(financialLedger);
//        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(user.getId()));
//        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
//    }

}
