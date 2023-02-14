package de.dhbw.plugins.persistence.hibernate.booking;

import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.booking.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class BookingRepositoryBridge implements BookingRepository {

    private final SpringDataBookingRepository repository;

    @Override
    public Optional<Booking> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Booking save(Booking booking) {
        return repository.save(booking);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
