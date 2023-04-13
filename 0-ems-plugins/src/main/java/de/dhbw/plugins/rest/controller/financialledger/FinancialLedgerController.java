package de.dhbw.plugins.rest.controller.financialledger;

import de.dhbw.ems.adapter.application.financialledger.FinancialLedgerApplicationAdapter;
import de.dhbw.ems.adapter.mapper.data.financialledger.FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper;
import de.dhbw.ems.application.domain.service.financialledger.data.FinancialLedgerData;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.plugins.rest.controller.utils.WebMvcLinkBuilderUtils;
import de.dhbw.plugins.rest.mapper.controller.factory.financialledger.FinancialLedgerModelFactory;
import de.dhbw.plugins.rest.mapper.model.financialledger.model.FinancialLedgerModel;
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

    private final FinancialLedgerApplicationAdapter financialLedgerApplicationAdapter;
    private final FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper adapterMapper;
    private final FinancialLedgerModelFactory financialLedgerModelFactory;

    @GetMapping
    public ResponseEntity<FinancialLedgerModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationAdapter.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedgerModel financialLedgerModel = financialLedgerModelFactory.create(userId, optionalFinancialLedger.get());
        return ResponseEntity.ok(financialLedgerModel);
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId, @Valid @RequestBody de.dhbw.plugins.rest.controller.financialledgers.data.FinancialLedgerData data) {
        Optional<FinancialLedger> optionalFinancialLedger = financialLedgerApplicationAdapter.find(userId, financialLedgerId);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedgerData financialLedgerData = adapterMapper.apply(data);
        optionalFinancialLedger = financialLedgerApplicationAdapter.updateByAttributeData(optionalFinancialLedger.get(), financialLedgerData);
        if (!optionalFinancialLedger.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = linkTo(methodOn(this.getClass()).findOne(userId, financialLedgerId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerId") UUID financialLedgerId) {
        if (!financialLedgerApplicationAdapter.delete(userId, financialLedgerId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
