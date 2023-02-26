package de.dhbw.ems.domain.booking.aggregate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingAggregateRepository {

    Optional<BookingAggregate> findById(UUID id);

    List<BookingAggregate> findByCreatorId(UUID id);

    BookingAggregate save(BookingAggregate bookingAggregate);

    void deleteById(UUID id);
}
