package de.dhbw.plugins.rest.mapper.model.bookingcategory.model;

import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;

import java.util.function.Function;

public interface BookingCategoryAggregateToBookingCategoryModelMapper extends Function<BookingCategoryAggregate, BookingCategoryModel> {
}
