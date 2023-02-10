package de.dhbw.ems.adapter.model.financialledger.model;

import de.dhbw.ems.adapter.model.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.ems.adapter.model.user.preview.UserPreviewCollectionModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@Setter
public class FinancialLedgerModel extends RepresentationModel<FinancialLedgerModel>{
    private String name;
    private UserPreviewCollectionModel userPreviewCollectionModel;
    private BookingCategoryPreviewCollectionModel bookingCategoryPreviewCollectionModel;
    private BookingPreviewCollectionModel bookingPreviewCollectionModel;
}
