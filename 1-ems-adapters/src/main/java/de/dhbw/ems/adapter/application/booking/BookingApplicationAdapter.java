package de.dhbw.ems.adapter.application.booking;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.application.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;

import java.util.Optional;
import java.util.UUID;

public interface BookingApplicationAdapter {

    Optional<BookingAggregate> find(UUID userId, UUID financialLedgerId, UUID bookingId);

    Optional<BookingAggregate> create(UUID userId, UUID financialLedgerId, BookingAggregateAttributeData attributeData);

    boolean exists(UUID userId, UUID financialLedgerId, UUID bookingId);

    boolean delete(UUID userId, UUID financialLedgerId, UUID bookingId);

    boolean referenceUser(UUID id, UUID financialLedgerId, UUID bookingId, UUID referenceUserId);

    boolean deleteUserReference(UUID id, UUID financialLedgerId, UUID bookingId);

    Optional<BookingAggregate> updateByAttributeData(BookingAggregate bookingAggregate, BookingAggregateAttributeData attributeData);

    boolean exchangeCurrencyOfBooking(UUID id, UUID financialLedgerId, UUID bookingId, CurrencyType targetCurrencyType);

}
