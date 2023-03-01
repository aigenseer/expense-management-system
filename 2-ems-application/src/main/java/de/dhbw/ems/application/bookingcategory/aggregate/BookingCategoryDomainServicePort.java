package de.dhbw.ems.application.bookingcategory.aggregate;

import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryDomainServicePort {

    Optional<BookingCategoryAggregate> findById(UUID id);

    Optional<BookingCategoryAggregate> updateByAttributeData(BookingCategoryAggregate bookingCategoryAggregate, BookingCategoryAttributeData data);
}
