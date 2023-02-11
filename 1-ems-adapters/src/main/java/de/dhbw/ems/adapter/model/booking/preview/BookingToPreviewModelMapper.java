package de.dhbw.ems.adapter.model.booking.preview;

import de.dhbw.ems.domain.booking.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingToPreviewModelMapper implements BookingToBookingPreviewModelAdapterMapper {

    @Override
    public BookingPreviewModel apply(final Booking category) {
        return map(category);
    }

    private BookingPreviewModel map(final Booking category) {
        return BookingPreviewModel.builder()
                .name(category.getTitle())
                .build();
    }
}
