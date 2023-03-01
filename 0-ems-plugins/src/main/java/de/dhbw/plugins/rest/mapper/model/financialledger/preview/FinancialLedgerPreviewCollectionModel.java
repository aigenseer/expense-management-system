package de.dhbw.plugins.rest.mapper.model.financialledger.preview;

import org.springframework.hateoas.CollectionModel;

public class FinancialLedgerPreviewCollectionModel extends CollectionModel<FinancialLedgerPreviewModel> {

    public FinancialLedgerPreviewCollectionModel(Iterable<FinancialLedgerPreviewModel> content){
        super(content);
    }

}
