package de.dhbw.ems.domain.booking.aggregate;

import java.util.Optional;
import java.util.UUID;

public interface BookingAggregateRepository {

    Optional<BookingAggregate> findById(UUID id);

    BookingAggregate save(BookingAggregate bookingAggregate);

    void deleteById(UUID id);
}
