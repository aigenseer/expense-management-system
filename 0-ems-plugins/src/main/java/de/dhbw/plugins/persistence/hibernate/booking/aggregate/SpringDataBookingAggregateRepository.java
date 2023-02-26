package de.dhbw.plugins.persistence.hibernate.booking.aggregate;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SpringDataBookingAggregateRepository extends JpaRepository<BookingAggregate, UUID> {

    @Query("SELECT b FROM BookingAggregate b WHERE b.creatorId = ?1")
    List<BookingAggregate> findByCreatorId(UUID creatorId);

}