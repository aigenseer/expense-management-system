package de.dhbw.ems.adapter.model.booking.preview;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;

import java.util.function.Function;

public interface BookingToBookingPreviewModelAdapterMapper extends Function<BookingAggregate, BookingPreviewModel> {
}
