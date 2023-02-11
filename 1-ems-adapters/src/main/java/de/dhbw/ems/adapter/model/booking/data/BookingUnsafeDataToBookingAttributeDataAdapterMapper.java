package de.dhbw.ems.adapter.model.booking.data;

import de.dhbw.ems.application.booking.BookingAttributeData;

import java.util.function.Function;

public interface BookingUnsafeDataToBookingAttributeDataAdapterMapper extends Function<IBookingUnsafeData, BookingAttributeData> {
}
