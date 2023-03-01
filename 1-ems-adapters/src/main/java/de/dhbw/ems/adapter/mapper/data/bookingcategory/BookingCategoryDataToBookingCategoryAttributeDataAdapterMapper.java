package de.dhbw.ems.adapter.mapper.data.bookingcategory;

import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;

import java.util.function.Function;

public interface BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper extends Function<IBookingCategoryData, BookingCategoryAttributeData> {
}
