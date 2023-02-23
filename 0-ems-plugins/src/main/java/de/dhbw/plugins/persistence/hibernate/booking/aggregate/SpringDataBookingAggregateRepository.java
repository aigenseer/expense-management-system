package de.dhbw.plugins.persistence.hibernate.booking.aggregate;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataBookingAggregateRepository extends JpaRepository<BookingAggregate, UUID> {

}