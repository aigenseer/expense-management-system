package de.dhbw.ems.domain.bookingcategory.aggregate;

import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryAggregateRepository {

    Optional<BookingCategory> findById(UUID id);

    BookingCategory save(BookingCategory bookingCategory);

    void deleteById(UUID id);
}
