package de.dhbw.ems.adapter.application.bookingcategory;

import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryApplicationAdapter {

    Optional<BookingCategoryAggregate> find(UUID id, UUID financialLedgerId, UUID bookingCategoryAggregateId);

    boolean exists(UUID id, UUID financialLedgerId, UUID bookingCategoryAggregateId);

    boolean delete(UUID id, UUID financialLedgerId, UUID bookingCategoryAggregateId);

    Optional<BookingCategoryAggregate> create(UUID id, UUID financialLedgerId, BookingCategoryAttributeData attributeData);

    Optional<BookingCategoryAggregate> updateByAttributeData(BookingCategoryAggregate bookingCategory, BookingCategoryAttributeData data);

}
