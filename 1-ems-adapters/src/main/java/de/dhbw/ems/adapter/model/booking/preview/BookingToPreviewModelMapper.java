package de.dhbw.ems.adapter.model.booking.preview;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import org.springframework.stereotype.Component;

@Component
public class BookingToPreviewModelMapper implements BookingToBookingPreviewModelAdapterMapper {

    @Override
    public BookingPreviewModel apply(final BookingAggregate bookingAggregate) {
        return map(bookingAggregate);
    }

    private BookingPreviewModel map(final BookingAggregate bookingAggregate) {
        return BookingPreviewModel.builder()
                .name(bookingAggregate.getBooking().getTitle())
                .build();
    }
}
