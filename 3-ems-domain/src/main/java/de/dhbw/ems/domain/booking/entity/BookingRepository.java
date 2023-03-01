package de.dhbw.ems.domain.booking.entity;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository {

    Optional<Booking> findById(UUID id);

    Booking save(Booking Booking);

    void deleteById(UUID id);
}
