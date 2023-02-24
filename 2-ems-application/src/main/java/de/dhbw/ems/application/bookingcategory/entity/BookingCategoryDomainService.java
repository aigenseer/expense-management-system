package de.dhbw.ems.application.bookingcategory.entity;

import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryDomainService {

    BookingCategory save(BookingCategory bookingCategory);

    void deleteById(UUID id);

    Optional<BookingCategory> createByAttributeData(BookingCategoryAttributeData data);

    Optional<BookingCategory> findById(UUID id);

    Optional<BookingCategory> updateByAttributeData(BookingCategory bookingCategory, BookingCategoryAttributeData data);

}
