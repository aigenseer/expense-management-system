package de.dhbw.ems.adapter.model.financialledger.preview;

import org.springframework.hateoas.CollectionModel;

public class FinancialLedgerPreviewCollectionModel extends CollectionModel<FinancialLedgerPreviewModel> {

    public FinancialLedgerPreviewCollectionModel(Iterable<FinancialLedgerPreviewModel> content){
        super(content);
    }

}
