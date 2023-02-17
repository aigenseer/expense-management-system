package de.dhbw.ems.application.booking;

import de.dhbw.ems.domain.booking.Booking;

import java.util.Optional;

public interface BookingDomainServicePort {

    Optional<Booking> updateByAttributeData(Booking booking, BookingAttributeData attributeData);
}
