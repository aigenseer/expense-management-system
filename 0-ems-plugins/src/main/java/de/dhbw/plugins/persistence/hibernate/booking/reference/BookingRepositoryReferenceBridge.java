package de.dhbw.plugins.persistence.hibernate.booking.reference;

import de.dhbw.ems.domain.booking.reference.BookingReference;
import de.dhbw.ems.domain.booking.reference.BookingReferenceId;
import de.dhbw.ems.domain.booking.reference.BookingReferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class BookingRepositoryReferenceBridge implements BookingReferenceRepository {

    private final SpringDataBookingReferenceRepository repository;

    @Override
    public Optional<BookingReference> findByIds(UUID userId, UUID bookingAggregateId) {
        return repository.findAllByIds(userId, bookingAggregateId).stream().findFirst();
    }

    @Override
    public List<BookingReference> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<BookingReference> findByBookingAggregateId(UUID bookingAggregateId) {
        return repository.findByBookingAggregateId(bookingAggregateId);
    }

    @Override
    public BookingReference save(BookingReference bookingReference) {
        return repository.save(bookingReference);
    }

    @Override
    public void deleteById(UUID userId, UUID bookingAggregateId) {
        repository.deleteById(BookingReferenceId.builder().userId(userId).bookingAggregateId(bookingAggregateId).build());
    }

    @Override
    public boolean exists(UUID userId, UUID bookingAggregateId) {
        return repository.existsById(BookingReferenceId.builder().userId(userId).bookingAggregateId(bookingAggregateId).build());
    }

}