package de.dhbw.ems.adapter.application.booking;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.application.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;

import java.util.Optional;
import java.util.UUID;

public interface BookingApplicationAdapter {

    Optional<BookingAggregate> find(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId);

    Optional<BookingAggregate> create(UUID userId, UUID financialLedgerAggregateId, BookingAggregateAttributeData attributeData);

    boolean exists(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId);

    boolean delete(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId);

    boolean referenceUser(UUID id, UUID financialLedgerAggregateId, UUID bookingAggregateId, UUID referenceUserId);

    boolean deleteUserReference(UUID id, UUID financialLedgerAggregateId, UUID bookingId);

    Optional<BookingAggregate> updateByAttributeData(BookingAggregate bookingAggregate, BookingAggregateAttributeData attributeData);

    boolean exchangeCurrencyOfBooking(UUID id, UUID financialLedgerAggregateId, UUID bookingAggregateId, CurrencyType targetCurrencyType);

}
