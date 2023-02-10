package de.dhbw.cleanproject.adapter.model.booking.preview;

import de.dhbw.cleanproject.domain.booking.Booking;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BookingToBookingPreviewModelAdapterMapper implements Function<Booking, BookingPreviewModel> {

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
