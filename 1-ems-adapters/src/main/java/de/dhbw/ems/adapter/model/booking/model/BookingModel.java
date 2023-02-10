package de.dhbw.ems.adapter.model.booking.model;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewModel;
import de.dhbw.ems.adapter.model.user.preview.UserPreview;
import de.dhbw.ems.adapter.model.user.preview.UserPreviewCollectionModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
public class BookingModel extends RepresentationModel<BookingModel>{
    private String title;
    @Setter
    private UserPreview creator;
    private Double amount;
    private CurrencyType currencyType;
    @Setter
    private UserPreviewCollectionModel referencedUsers;
    @Setter
    private BookingCategoryPreviewModel category;
}
