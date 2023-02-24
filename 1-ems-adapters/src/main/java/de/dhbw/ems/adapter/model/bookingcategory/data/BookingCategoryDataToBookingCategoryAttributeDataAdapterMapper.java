package de.dhbw.ems.adapter.model.bookingcategory.data;

import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;

import java.util.function.Function;

public interface BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper extends Function<BookingCategoryData, BookingCategoryAttributeData> {
}
