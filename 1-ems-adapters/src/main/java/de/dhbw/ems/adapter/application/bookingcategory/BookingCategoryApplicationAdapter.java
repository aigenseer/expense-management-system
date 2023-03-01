package de.dhbw.ems.adapter.application.bookingcategory;

import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryApplicationAdapter {

    Optional<BookingCategoryAggregate> find(UUID id, UUID financialLedgerAggregateId, UUID bookingCategoryAggregateId);

    boolean exists(UUID id, UUID financialLedgerAggregateId, UUID bookingCategoryAggregateId);

    boolean delete(UUID id, UUID financialLedgerAggregateId, UUID bookingCategoryAggregateId);

    Optional<BookingCategoryAggregate> create(UUID id, UUID financialLedgerAggregateId, BookingCategoryAttributeData attributeData);

    Optional<BookingCategoryAggregate> updateByAttributeData(BookingCategoryAggregate bookingCategoryAggregate, BookingCategoryAttributeData data);

}
