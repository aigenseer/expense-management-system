package de.dhbw.ems.adapter.model.financialledger.preview;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Builder
@Getter
public class FinancialLedgerPreviewModel extends RepresentationModel<FinancialLedgerPreviewModel>{
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FinancialLedgerPreviewModel that = (FinancialLedgerPreviewModel) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
