package de.dhbw.ems.adapter.model.bookingcategory.data;

import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;

import java.util.function.Function;

public interface BookingCategoryToBookingCategoryModelAdapterMapper extends Function<BookingCategory, BookingCategoryModel> {
}
