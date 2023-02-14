package de.dhbw.ems.domain.booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository {

    Optional<Booking> findById(UUID id);

    Booking save(Booking booking);

    void deleteById(UUID id);
}
