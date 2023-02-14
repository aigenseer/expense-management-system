package de.dhbw.plugins.persistence.hibernate.booking;

import de.dhbw.ems.domain.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataBookingRepository extends JpaRepository<Booking, UUID> {

}
