package de.dhbw.plugins.rest.mapper.model.financialledger.model;

import de.dhbw.plugins.rest.mapper.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.plugins.rest.mapper.model.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.plugins.rest.mapper.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Builder
@Getter
@Setter
public class FinancialLedgerModel extends RepresentationModel<FinancialLedgerModel>{
    private String name;
    private UserPreviewCollectionModel userPreviewCollectionModel;
    private BookingCategoryPreviewCollectionModel bookingCategoryPreviewCollectionModel;
    private BookingPreviewCollectionModel bookingPreviewCollectionModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FinancialLedgerModel that = (FinancialLedgerModel) o;
        return Objects.equals(name, that.name) && Objects.equals(userPreviewCollectionModel, that.userPreviewCollectionModel) && Objects.equals(bookingCategoryPreviewCollectionModel, that.bookingCategoryPreviewCollectionModel) && Objects.equals(bookingPreviewCollectionModel, that.bookingPreviewCollectionModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, userPreviewCollectionModel, bookingCategoryPreviewCollectionModel, bookingPreviewCollectionModel);
    }
}
