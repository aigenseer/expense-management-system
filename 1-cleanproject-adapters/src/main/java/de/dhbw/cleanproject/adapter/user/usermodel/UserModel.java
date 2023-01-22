package de.dhbw.cleanproject.adapter.user.usermodel;

import de.dhbw.cleanproject.adapter.financialledger.preview.FinancialLedgerPreviewCollectionModel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
public class UserModel extends RepresentationModel<UserModel>{
    private String name;
    private String email;
    private String phoneNumber;
    private FinancialLedgerPreviewCollectionModel financialLedgers;
}
