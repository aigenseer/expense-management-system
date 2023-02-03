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
import de.dhbw.plugins.rest.user.UserController;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.AllArgsConstructor;
import org.javatuples.Pair;
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
@RequestMapping(value = "/api/user/{userId}/financialledger/{financialLedgerId}/", produces = "application/vnd.siren+json")
@AllArgsConstructor

public class FinancialLedgerController {

    private final UserOperationService userOperationService;
    private final FinancialLedgerApplicationService financialLedgerApplicationService;
    private final FinancialLedgerToFinancialLedgerPreviewModelMapper previewModelMapper;
    private final FinancialLedgerUpdateDataToFinancialLedgerMapper updateDataMapper;
    private final FinancialLedgerToFinancialLedgerModelMapper modelMapper;
    private final UserToUserPreviewModelMapper userToUserPreviewModelMapper;

    @GetMapping
    public ResponseEntity<FinancialLedgerModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = optionalFinancialLedger.get();

        List<UserPreview> userPreviewModels = financialLedger.getAuthorizedUser().stream()
                .map(user -> {
                    UserPreview userPreview = userToUserPreviewModelMapper.apply(user);
                    Link selfLink = linkTo(methodOn(UserController.class).findOne(user.getId())).withSelfRel();
                    userPreview.add(selfLink);
                    return userPreview;
                })
                .collect(Collectors.toList());
        UserPreviewCollectionModel userPreviewCollectionModel = new UserPreviewCollectionModel(userPreviewModels);

        Link selfLink = linkTo(methodOn(getClass()).findOne(userId, financialLedgerId)).withSelfRel()
                .andAffordance(afford(methodOn(getClass()).update(userId, financialLedgerId, null)))
                .andAffordance(afford(methodOn(getClass()).delete(userId, financialLedgerId)));
        userPreviewCollectionModel.add(selfLink);

        FinancialLedgerModel model = modelMapper.apply(Pair.with(financialLedger, userPreviewCollectionModel));

        return ResponseEntity.ok(model);
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody FinancialLedgerData data) {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedger financialLedger = updateDataMapper.apply(Pair.with(optionalFinancialLedger.get(), data));
        financialLedgerApplicationService.save(financialLedger);
        WebMvcLinkBuilder uriComponents = linkTo(methodOn(FinancialLedgerController.class).findOne(userId, financialLedgerId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        if (!userOperationService.deleteFinancialLedgerById(userId, financialLedgerId)) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
