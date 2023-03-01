package de.dhbw.plugins.rest.mapper.model.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.dhbw.ems.adapter.model.financialledger.preview.FinancialLedgerPreviewCollectionModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(id, userModel.id) && Objects.equals(name, userModel.name) && Objects.equals(email, userModel.email) && Objects.equals(phoneNumber, userModel.phoneNumber) && Objects.equals(financialLedgerPreviewCollectionModel, userModel.financialLedgerPreviewCollectionModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, email, phoneNumber, financialLedgerPreviewCollectionModel);
    }
}
