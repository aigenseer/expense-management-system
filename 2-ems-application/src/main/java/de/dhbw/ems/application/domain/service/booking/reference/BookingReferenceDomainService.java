package de.dhbw.ems.application.domain.service.booking.reference;

import de.dhbw.ems.domain.booking.reference.BookingReference;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingReferenceDomainService {

    Optional<BookingReference> findById(UUID userId, UUID bookingAggregateId);

    Optional<BookingReference> create(UUID userId, UUID bookingAggregateId);

    List<BookingReference> findByUserId(UUID userId);

    List<BookingReference> findByBookingAggregateId(UUID bookingAggregateId);

    void deleteById(UUID userId, UUID bookingAggregateId);

    boolean exists(UUID userId, UUID bookingAggregateId);

}
