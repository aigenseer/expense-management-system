package de.dhbw.ems.application.mediator.service.impl;

import de.dhbw.ems.application.domain.service.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryServicePort {

    Optional<BookingCategoryAggregate> find(UUID userId, UUID financialLedgerAggregateId, UUID bookingCategoryAggregateId);

    boolean exists(UUID userId, UUID financialLedgerAggregateId, UUID bookingCategoryAggregateId);

    boolean delete(UUID userId, UUID financialLedgerAggregateId, UUID bookingCategoryAggregateId);

    Optional<BookingCategoryAggregate> create(UUID userId, UUID financialLedgerAggregateId, BookingCategoryAttributeData attributeData);

}
