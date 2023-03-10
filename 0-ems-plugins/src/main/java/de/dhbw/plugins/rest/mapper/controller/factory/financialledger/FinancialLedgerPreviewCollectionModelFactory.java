package de.dhbw.plugins.rest.mapper.controller.factory.financialledger;

import de.dhbw.plugins.rest.controller.financialledgers.FinancialLedgersController;
import de.dhbw.plugins.rest.mapper.controller.model.financialledger.FinancialLedgersToFinancialLedgerPreviewCollectionMapper;
import de.dhbw.plugins.rest.mapper.model.financialledger.preview.FinancialLedgerPreviewCollectionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class FinancialLedgerPreviewCollectionModelFactory {

    private final FinancialLedgersToFinancialLedgerPreviewCollectionMapper financialLedgersToFinancialLedgerPreviewCollectionMapper;

    public FinancialLedgerPreviewCollectionModel create(final UUID userId) {
        FinancialLedgerPreviewCollectionModel previewCollectionModel = financialLedgersToFinancialLedgerPreviewCollectionMapper.apply(userId);

        Link selfLink = linkTo(methodOn(FinancialLedgersController.class).listAll(userId)).withSelfRel()
                .andAffordance(afford(methodOn(FinancialLedgersController.class).create(userId, null)));
        previewCollectionModel.add(selfLink);
        return previewCollectionModel;
    }
}
