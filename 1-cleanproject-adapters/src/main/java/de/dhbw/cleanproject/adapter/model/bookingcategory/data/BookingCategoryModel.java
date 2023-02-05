package de.dhbw.cleanproject.adapter.model.bookingcategory.data;

import de.dhbw.cleanproject.adapter.model.booking.preview.BookingPreviewCollectionModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
public class BookingCategoryModel extends RepresentationModel<BookingCategoryModel>{
    private String title;
    @Setter
    private BookingPreviewCollectionModel bookingPreviewCollectionModel;
}
