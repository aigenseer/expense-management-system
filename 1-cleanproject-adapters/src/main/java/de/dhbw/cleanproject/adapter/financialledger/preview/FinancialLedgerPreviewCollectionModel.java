package de.dhbw.cleanproject.adapter.financialledger.preview;

import org.springframework.hateoas.CollectionModel;

public class FinancialLedgerPreviewCollectionModel extends CollectionModel<FinancialLedgerPreviewModel> {

    public FinancialLedgerPreviewCollectionModel(Iterable<FinancialLedgerPreviewModel> content){
        super(content);
    }

}
