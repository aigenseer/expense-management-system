package de.dhbw.cleanproject.domain.bookingcategory;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryRepository {

    Optional<BookingCategory> findById(UUID id);

    BookingCategory save(BookingCategory bookingCategory);

    void deleteById(UUID id);
}
