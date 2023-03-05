package de.dhbw.plugins.rest.mapper.model.booking.model;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;

import java.util.function.Function;

public interface BookingAggregateToBookingModelMapper extends Function<BookingAggregate, BookingModel> {

}
