package de.dhbw.plugins.persistence.hibernate.booking.aggregate;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class BookingAggregateRepositoryBridge implements BookingAggregateRepository {

    private final SpringDataBookingAggregateRepository repository;

    @Override
    public Optional<BookingAggregate> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public BookingAggregate save(BookingAggregate bookingAggregate) {
        return repository.save(bookingAggregate);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
