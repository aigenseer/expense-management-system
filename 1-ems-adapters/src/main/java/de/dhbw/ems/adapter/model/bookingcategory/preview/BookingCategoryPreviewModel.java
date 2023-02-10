package de.dhbw.ems.adapter.model.bookingcategory.preview;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
public class BookingCategoryPreviewModel extends RepresentationModel<BookingCategoryPreviewModel>{
    private String title;
}
