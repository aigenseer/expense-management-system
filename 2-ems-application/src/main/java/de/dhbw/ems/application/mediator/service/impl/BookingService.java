package de.dhbw.ems.application.mediator.service.impl;

import java.util.UUID;

public interface BookingService extends BookingServicePort {

    boolean deleteUserReference(UUID id, UUID financialLedgerId, UUID bookingId);

}
