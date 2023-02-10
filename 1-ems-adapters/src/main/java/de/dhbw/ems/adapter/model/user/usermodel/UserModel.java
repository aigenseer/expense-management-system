package de.dhbw.ems.adapter.model.user.usermodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.dhbw.ems.adapter.model.financialledger.preview.FinancialLedgerPreviewCollectionModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Builder
@Getter
public class UserModel extends RepresentationModel<UserModel>{
    @JsonIgnore
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    @Setter
    private FinancialLedgerPreviewCollectionModel financialLedgerPreviewCollectionModel;
}
