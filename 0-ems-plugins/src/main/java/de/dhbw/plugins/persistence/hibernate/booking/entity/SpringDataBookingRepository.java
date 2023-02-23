package de.dhbw.plugins.persistence.hibernate.booking.entity;

import de.dhbw.ems.domain.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataBookingRepository extends JpaRepository<Booking, UUID> {

}
