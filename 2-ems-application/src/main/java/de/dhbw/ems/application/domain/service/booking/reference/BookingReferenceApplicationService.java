package de.dhbw.ems.application.domain.service.booking.reference;

import de.dhbw.ems.domain.booking.reference.BookingReference;
import de.dhbw.ems.domain.booking.reference.BookingReferenceId;
import de.dhbw.ems.domain.booking.reference.BookingReferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingReferenceApplicationService implements BookingReferenceDomainService {

    private final BookingReferenceRepository repository;

    @Override
    public Optional<BookingReference> findById(UUID userId, UUID bookingAggregateId) {
        return repository.findByIds(userId, bookingAggregateId);
    }

    @Override
    public Optional<BookingReference> create(UUID userId, UUID bookingAggregateId) {
        BookingReference bookingReference = BookingReference.builder().id(BookingReferenceId.builder().userId(userId).bookingAggregateId(bookingAggregateId).build()).build();
        return Optional.of(repository.save(bookingReference));
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
    public void deleteById(UUID userId, UUID bookingAggregateId) {
        repository.deleteById(userId, bookingAggregateId);
    }

    @Override
    public boolean exists(UUID userId, UUID bookingAggregateId) {
        return repository.exists(userId, bookingAggregateId);
    }
}
