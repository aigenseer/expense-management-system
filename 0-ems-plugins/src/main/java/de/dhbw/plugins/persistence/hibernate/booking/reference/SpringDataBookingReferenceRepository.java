package de.dhbw.plugins.persistence.hibernate.booking.reference;

import de.dhbw.ems.domain.booking.reference.BookingReference;
import de.dhbw.ems.domain.booking.reference.BookingReferenceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SpringDataBookingReferenceRepository extends JpaRepository<BookingReference, BookingReferenceId> {

    @Query("SELECT r FROM BookingReference r WHERE r.id.userId = ?1 and r.id.bookingAggregateId = ?2")
    List<BookingReference> findAllByIds(UUID userId, UUID bookingAggregateId);

    @Query("SELECT r FROM BookingReference r WHERE r.id.userId = ?1")
    List<BookingReference> findByUserId(UUID userId);

    @Query("SELECT r FROM BookingReference r WHERE r.id.bookingAggregateId = ?1")
    List<BookingReference> findByBookingAggregateId(UUID bookingAggregateId);

}
