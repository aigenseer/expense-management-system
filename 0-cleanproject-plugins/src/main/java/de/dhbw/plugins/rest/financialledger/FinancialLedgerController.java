package de.dhbw.plugins.rest.financialledger;

import de.dhbw.cleanproject.adapter.financialledger.data.FinancialLedgerData;
import de.dhbw.cleanproject.adapter.financialledger.data.FinancialLedgerUpdateDataToFinancialLedgerMapper;
import de.dhbw.cleanproject.adapter.financialledger.model.FinancialLedgerModel;
import de.dhbw.cleanproject.adapter.financialledger.model.FinancialLedgerToFinancialLedgerModelMapper;
import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerToFinancialLedgerPreviewModelMapper;
import de.dhbw.cleanproject.adapter.user.preview.UserPreview;
import de.dhbw.cleanproject.adapter.user.preview.UserPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.user.preview.UserToUserPreviewModelMapper;
import de.dhbw.cleanproject.application.FinancialLedgerApplicationService;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.AllArgsConstructor;
import org.javatuples.Pair;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/user/{userId}/financialledger/{id}/", produces = "application/vnd.siren+json")
@AllArgsConstructor

public class FinancialLedgerController {

    private final UserOperationService userOperationService;
    private final FinancialLedgerApplicationService financialLedgerApplicationService;
    private final FinancialLedgerToFinancialLedgerPreviewModelMapper previewModelMapper;
    private final FinancialLedgerUpdateDataToFinancialLedgerMapper updateDataMapper;
    private final FinancialLedgerToFinancialLedgerModelMapper modelMapper;
    private final UserToUserPreviewModelMapper userToUserPreviewModelMapper;

    @GetMapping
    public ResponseEntity<FinancialLedgerModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("id") UUID id) {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, id);
        if (!optionalFinancialLedger.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();

        List<UserPreview> userPreviewModels = financialLedger.getAuthorizedUser().stream()
                .map(user -> {
                    UserPreview userPreview = userToUserPreviewModelMapper.apply(user);
//                    Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel();
//                    userPreview.add(selfLink);
                    return userPreview;
                })
                .collect(Collectors.toList());
        UserPreviewCollectionModel userPreviewCollectionModel = new UserPreviewCollectionModel(userPreviewModels);

//        Link selfLink = linkTo(methodOn(UsersController.class).listAll()).withSelfRel()
//                .andAffordance(afford(methodOn(UsersController.class).create(null)));
//        userPreviewCollectionModel.add(selfLink);

        FinancialLedgerModel model = modelMapper.apply(Pair.with(financialLedger, userPreviewCollectionModel));

        return ResponseEntity.ok(model);
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("id") UUID id, @Valid @RequestBody FinancialLedgerData data) {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, id);
        if (!optionalFinancialLedger.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = updateDataMapper.apply(Pair.with(optionalFinancialLedger.get(), data));
        financialLedgerApplicationService.save(financialLedger);
        WebMvcLinkBuilder uriComponents = WebMvcLinkBuilder.linkTo(methodOn(FinancialLedgerController.class).findOne(userId, id));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        return null;
    }

}
