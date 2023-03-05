package de.dhbw.ems.application.mediator.service.impl;

import de.dhbw.ems.application.domain.service.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;

import java.util.Optional;
import java.util.UUID;

public interface BookingServicePort {

    Optional<BookingAggregate> find(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId);

    Optional<BookingAggregate> create(UUID userId, UUID financialLedgerAggregateId, BookingAggregateAttributeData attributeData);

    boolean exists(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId);

    boolean delete(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId);

    boolean referenceUser(UUID id, UUID financialLedgerAggregateId, UUID bookingAggregateId, UUID referenceUserId);

}
