package de.dhbw.ems.domain.bookingcategory.aggregate;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryAggregateRepository {

    Optional<BookingCategoryAggregate> findById(UUID id);

    BookingCategoryAggregate save(BookingCategoryAggregate bookingCategory);

    void deleteById(UUID id);
}
