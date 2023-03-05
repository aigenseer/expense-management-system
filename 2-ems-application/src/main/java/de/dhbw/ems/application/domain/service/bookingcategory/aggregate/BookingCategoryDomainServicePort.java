package de.dhbw.ems.application.domain.service.bookingcategory.aggregate;

import de.dhbw.ems.application.domain.service.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryDomainServicePort {

    Optional<BookingCategoryAggregate> findById(UUID id);

    Optional<BookingCategoryAggregate> updateByAttributeData(BookingCategoryAggregate bookingCategoryAggregate, BookingCategoryAttributeData data);
}
