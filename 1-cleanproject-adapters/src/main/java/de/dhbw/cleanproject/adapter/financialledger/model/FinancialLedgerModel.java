package de.dhbw.cleanproject.adapter.financialledger.model;

import de.dhbw.cleanproject.adapter.user.preview.UserPreviewCollectionModel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
public class FinancialLedgerModel extends RepresentationModel<FinancialLedgerModel>{
    private String name;
    private UserPreviewCollectionModel userPreviewCollectionModel;
}
