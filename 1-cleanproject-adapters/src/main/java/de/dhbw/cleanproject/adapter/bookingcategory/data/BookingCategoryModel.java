package de.dhbw.cleanproject.adapter.bookingcategory.data;

import de.dhbw.cleanproject.adapter.booking.preview.BookingPreviewCollectionModel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
public class BookingCategoryModel extends RepresentationModel<BookingCategoryModel>{
    private String title;
    private BookingPreviewCollectionModel bookingPreviewCollectionModel;
}
