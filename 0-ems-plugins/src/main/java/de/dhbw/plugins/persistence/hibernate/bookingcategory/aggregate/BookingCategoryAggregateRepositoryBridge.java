package de.dhbw.plugins.persistence.hibernate.bookingcategory.aggregate;

import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class BookingCategoryAggregateRepositoryBridge implements BookingCategoryAggregateRepository {

    private final SpringDataBookingCategoryAggregateRepository repository;

    @Override
    public Optional<BookingCategoryAggregate> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public BookingCategoryAggregate save(BookingCategoryAggregate bookingCategoryAggregate) {
        return repository.save(bookingCategoryAggregate);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
