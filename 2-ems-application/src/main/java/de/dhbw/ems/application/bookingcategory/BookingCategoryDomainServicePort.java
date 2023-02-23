package de.dhbw.ems.application.bookingcategory;

import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryDomainServicePort {

    Optional<BookingCategory> findById(UUID id);

    Optional<BookingCategory> updateByAttributeData(BookingCategory bookingCategory, BookingCategoryAttributeData data);
}
