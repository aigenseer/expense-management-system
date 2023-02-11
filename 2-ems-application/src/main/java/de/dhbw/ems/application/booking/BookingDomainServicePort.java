package de.dhbw.ems.application.booking;

import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingDomainServicePort {

    List<Booking> findAllWithFinancialLedgerId(UUID financialLedgerId);

    Optional<Booking> findById(UUID id);

    Booking save(Booking booking);

    void deleteById(UUID id);

    Optional<Booking> createByAttributeData(User user, FinancialLedger financialLedger, BookingAttributeData attributeData);

    Optional<Booking> updateByAttributeData(Booking booking, BookingAttributeData attributeData);
}
