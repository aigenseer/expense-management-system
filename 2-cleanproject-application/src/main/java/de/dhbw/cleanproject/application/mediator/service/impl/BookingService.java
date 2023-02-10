package de.dhbw.cleanproject.application.mediator.service.impl;

import de.dhbw.cleanproject.application.booking.BookingAttributeData;
import de.dhbw.cleanproject.domain.booking.Booking;

import java.util.Optional;
import java.util.UUID;

public interface BookingService {

    Optional<Booking> find(UUID userId, UUID financialLedgerId, UUID bookingId);

    Optional<Booking> create(UUID userId, UUID financialLedgerId, BookingAttributeData attributeData);

    boolean exists(UUID userId, UUID financialLedgerId, UUID bookingId);

    boolean delete(UUID userId, UUID financialLedgerId, UUID bookingId);

    boolean referenceUser(UUID id, UUID financialLedgerId, UUID bookingId, UUID referenceUserId);

    boolean deleteUserReference(UUID id, UUID financialLedgerId, UUID bookingId);

}
