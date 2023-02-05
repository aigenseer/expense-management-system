package de.dhbw.cleanproject.adapter.model.financialledger.preview;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
public class FinancialLedgerPreviewModel extends RepresentationModel<FinancialLedgerPreviewModel>{
    private String name;
}