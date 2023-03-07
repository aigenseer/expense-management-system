package de.dhbw.ems.adapter.mapper.data.bookingcategory;

import de.dhbw.ems.application.domain.service.bookingcategory.data.BookingCategoryAttributeData;

import java.util.function.Function;

public interface BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper extends Function<IBookingCategoryData, BookingCategoryAttributeData> {
}
