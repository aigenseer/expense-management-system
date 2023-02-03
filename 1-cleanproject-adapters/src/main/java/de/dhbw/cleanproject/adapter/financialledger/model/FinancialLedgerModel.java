package de.dhbw.cleanproject.adapter.financialledger.model;

import de.dhbw.cleanproject.adapter.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.user.preview.UserPreviewCollectionModel;
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
