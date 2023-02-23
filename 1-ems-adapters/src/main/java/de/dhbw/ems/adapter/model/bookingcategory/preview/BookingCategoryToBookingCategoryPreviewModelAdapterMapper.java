package de.dhbw.ems.adapter.model.bookingcategory.preview;

import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;

import java.util.function.Function;

public interface BookingCategoryToBookingCategoryPreviewModelAdapterMapper extends Function<BookingCategory, BookingCategoryPreviewModel> {
}
