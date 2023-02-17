package de.dhbw.ems.application.booking;

import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;

import java.util.Optional;
import java.util.UUID;

public interface BookingDomainServicePort {

    Optional<Booking> updateByAttributeData(Booking booking, BookingAttributeData attributeData);
}
