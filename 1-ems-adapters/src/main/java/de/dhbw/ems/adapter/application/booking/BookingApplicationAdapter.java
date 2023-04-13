package de.dhbw.ems.adapter.application.booking;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.application.domain.service.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;

import java.util.Optional;
import java.util.UUID;

public interface BookingApplicationAdapter {

    Optional<BookingAggregate> find(UUID userId, UUID financialLedgerId, UUID bookingAggregateId);

    Optional<BookingAggregate> create(UUID userId, UUID financialLedgerId, BookingAggregateAttributeData attributeData);

    boolean exists(UUID userId, UUID financialLedgerId, UUID bookingAggregateId);

    boolean delete(UUID userId, UUID financialLedgerId, UUID bookingAggregateId);

    boolean referenceUser(UUID id, UUID financialLedgerId, UUID bookingAggregateId, UUID referenceUserId);

    boolean deleteUserReference(UUID userId, UUID financialLedgerId, UUID bookingAggregateId, UUID referenceUserId);

    Optional<BookingAggregate> updateByAttributeData(BookingAggregate bookingAggregate, BookingAggregateAttributeData attributeData);

    boolean exchangeCurrencyOfBooking(UUID id, UUID financialLedgerId, UUID bookingAggregateId, CurrencyType targetCurrencyType);

}
