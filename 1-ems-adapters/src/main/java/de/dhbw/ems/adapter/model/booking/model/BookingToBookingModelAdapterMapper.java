package de.dhbw.ems.adapter.model.booking.model;

import de.dhbw.ems.domain.booking.Booking;

import java.util.function.Function;

public interface BookingToBookingModelAdapterMapper extends Function<Booking, BookingModel> {

}
