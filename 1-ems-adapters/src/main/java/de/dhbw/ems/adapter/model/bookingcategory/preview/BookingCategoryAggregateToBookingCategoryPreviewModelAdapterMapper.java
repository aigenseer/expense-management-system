package de.dhbw.ems.adapter.model.bookingcategory.preview;

import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;

import java.util.function.Function;

public interface BookingCategoryAggregateToBookingCategoryPreviewModelAdapterMapper extends Function<BookingCategoryAggregate, BookingCategoryPreviewModel> {
}
