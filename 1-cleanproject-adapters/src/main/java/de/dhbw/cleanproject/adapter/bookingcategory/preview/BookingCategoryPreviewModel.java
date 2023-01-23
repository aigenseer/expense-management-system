package de.dhbw.cleanproject.adapter.bookingcategory.preview;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
public class BookingCategoryPreviewModel extends RepresentationModel<BookingCategoryPreviewModel>{
    private String name;
    private String email;
    private String phoneNumber;
}
