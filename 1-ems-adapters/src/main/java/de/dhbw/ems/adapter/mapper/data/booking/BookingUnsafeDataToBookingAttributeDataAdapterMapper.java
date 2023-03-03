package de.dhbw.ems.adapter.mapper.data.booking;

import de.dhbw.ems.application.domain.service.booking.data.BookingAggregateAttributeData;

import java.util.function.Function;

public interface BookingUnsafeDataToBookingAttributeDataAdapterMapper extends Function<IBookingUnsafeData, BookingAggregateAttributeData> {
}
