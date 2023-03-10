package de.dhbw.plugins.rest.mapper.model.bookingcategory.preview;

import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;

import java.util.function.Function;

public interface BookingCategoryAggregateToBookingCategoryPreviewModelMapper extends Function<BookingCategoryAggregate, BookingCategoryPreviewModel> {
}
