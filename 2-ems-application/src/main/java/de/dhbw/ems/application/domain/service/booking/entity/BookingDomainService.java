package de.dhbw.ems.application.domain.service.booking.entity;

import de.dhbw.ems.application.domain.service.booking.data.BookingAttributeData;
import de.dhbw.ems.domain.booking.entity.Booking;

import java.util.Optional;
import java.util.UUID;

public interface BookingDomainService{

    Optional<Booking> findById(UUID id);

    Booking save(Booking booking);

    void deleteById(UUID id);

    Optional<Booking> createByAttributeData(BookingAttributeData attributeData);

    Optional<Booking> updateByAttributeData(Booking booking, BookingAttributeData attributeData);

}
