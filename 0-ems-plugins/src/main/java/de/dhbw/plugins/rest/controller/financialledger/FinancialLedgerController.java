package de.dhbw.plugins.rest.controller.financialledger;

import de.dhbw.ems.adapter.application.financialledger.FinancialLedgerApplicationAdapter;
import de.dhbw.ems.adapter.model.financialledger.data.FinancialLedgerData;
import de.dhbw.ems.adapter.model.financialledger.data.FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper;
import de.dhbw.ems.adapter.model.financialledger.model.FinancialLedgerModel;
import de.dhbw.ems.application.financialledger.data.FinancialLedgerAttributeData;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import de.dhbw.plugins.mapper.financialledger.FinancialLedgerModelFactory;
import de.dhbw.plugins.rest.controller.utils.WebMvcLinkBuilderUtils;
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
@RequestMapping(value = "/api/user/{userId}/financialledger/{financialLedgerAggregateId}/", produces = "application/vnd.siren+json")
@AllArgsConstructor

public class FinancialLedgerController {

    private final FinancialLedgerApplicationAdapter financialLedgerApplicationAdapter;
    private final FinancialLedgerDataToFinancialLedgerAttributeDataAdapterMapper adapterMapper;
    private final FinancialLedgerModelFactory financialLedgerModelFactory;

    @GetMapping
    public ResponseEntity<FinancialLedgerModel> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId) {
        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerApplicationAdapter.find(userId, financialLedgerAggregateId);
        if (!optionalFinancialLedgerAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedgerModel financialLedgerModel = financialLedgerModelFactory.create(userId, optionalFinancialLedgerAggregate.get());
        return ResponseEntity.ok(financialLedgerModel);
    }

    @PutMapping
    public ResponseEntity<Void> update(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId, @Valid @RequestBody FinancialLedgerData data) {
        Optional<FinancialLedgerAggregate> optionalFinancialLedgerAggregate = financialLedgerApplicationAdapter.find(userId, financialLedgerAggregateId);
        if (!optionalFinancialLedgerAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        FinancialLedgerAttributeData financialLedgerAttributeData = adapterMapper.apply(data);
        optionalFinancialLedgerAggregate = financialLedgerApplicationAdapter.updateByAttributeData(optionalFinancialLedgerAggregate.get(), financialLedgerAttributeData);
        if (!optionalFinancialLedgerAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        WebMvcLinkBuilder uriComponents = linkTo(methodOn(this.getClass()).findOne(userId, financialLedgerAggregateId));
        return new ResponseEntity<>(WebMvcLinkBuilderUtils.createLocationHeader(uriComponents), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId) {
        if (!financialLedgerApplicationAdapter.delete(userId, financialLedgerAggregateId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
