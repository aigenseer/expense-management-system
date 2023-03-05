package de.dhbw.plugins.rest.mapper.model.booking.preview;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;

import java.util.function.Function;

public interface BookingAggregateToBookingPreviewModelMapper extends Function<BookingAggregate, BookingPreviewModel> {
}
