package de.dhbw.ems.domain.booking.reference;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingReferenceRepository {

    Optional<BookingReference> findByIds(UUID userId, UUID bookingAggregateId);

    List<BookingReference> findByUserId(UUID userId);

    List<BookingReference> findByBookingAggregateId(UUID bookingAggregateId);

    BookingReference save(BookingReference bookingAggregate);

    void deleteById(UUID userId, UUID bookingAggregateId);

    boolean exists(UUID userId, UUID bookingAggregateId);
}
