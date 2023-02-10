package de.dhbw.ems.adapter.model.booking.preview;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
public class BookingPreviewModel extends RepresentationModel<BookingPreviewModel>{
    private String name;
}