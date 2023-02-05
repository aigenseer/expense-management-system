package de.dhbw.plugins.rest.financialledger;

import de.dhbw.cleanproject.adapter.model.financialledger.data.FinancialLedgerData;
import de.dhbw.cleanproject.adapter.model.financialledger.data.FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper;
import de.dhbw.cleanproject.adapter.model.financialledger.model.FinancialLedgerModel;
import de.dhbw.cleanproject.application.UserOperationService;
import de.dhbw.cleanproject.application.financialledger.FinancialLedgerApplicationService;
import de.dhbw.cleanproject.application.financialledger.FinancialLedgerAttributeData;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.plugins.mapper.financialledger.FinancialLedgerModelFactory;
import de.dhbw.plugins.rest.utils.WebMvcLinkBuilderUtils;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/user/{userId}/financialledger/{financialLedgerId}/", produces = "application/vnd.siren+json")
@AllArgsConstructor

public class FinancialLedgerController {

    private final UserOperationService userOperationService;
    private final FinancialLedgerApplicationService financialLedgerApplicationService;
    private final FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper adapterMapper;
    private final FinancialLedgerModelFactory financialLedgerModelFactory;

    @GetMapping
    public ResponseEntity<FinancialLedgerModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedgerModel financialLedgerModel = financialLedgerModelFactory.create(userId, optionalFinancialLedger.get());
        return ResponseEntity.ok(financialLedgerModel);
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody FinancialLedgerData data) {
        Optional<FinancialLedger> optionalFinancialLedger = userOperationService.findFinancialLedgerByUserId(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedgerAttributeData financialLedgerAttributeData = adapterMapper.apply(data);
        optionalFinancialLedger = financialLedgerApplicationService.updateByAttributeData(optionalFinancialLedger.get(), financialLedgerAttributeData);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = linkTo(methodOn(this.getClass()).findOne(userId, financialLedgerId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        if (!userOperationService.deleteFinancialLedgerById(userId, financialLedgerId)) new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
