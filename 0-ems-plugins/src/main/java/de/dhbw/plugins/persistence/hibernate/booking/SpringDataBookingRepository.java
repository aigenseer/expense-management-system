package de.dhbw.plugins.persistence.hibernate.booking;

import de.dhbw.cleanproject.domain.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SpringDataBookingRepository extends JpaRepository<Booking, UUID> {

    @Query("SELECT s FROM Booking s WHERE s.financialLedgerId = ?1")
    List<Booking> findAllWithFinancialLedgerId(UUID financialLedgerId);

}
