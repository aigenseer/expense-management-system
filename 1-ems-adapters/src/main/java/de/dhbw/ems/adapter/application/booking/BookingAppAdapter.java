package de.dhbw.ems.adapter.application.booking;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.application.booking.BookingAttributeData;
import de.dhbw.ems.application.booking.BookingDomainServicePort;
import de.dhbw.ems.application.mediator.service.impl.BookingServicePort;
import de.dhbw.ems.application.mediator.service.impl.ExchangeCurrencyServicePort;
import de.dhbw.ems.domain.booking.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingAppAdapter implements BookingApplicationAdapter {

    private final BookingServicePort bookingServicePort;
    private final ExchangeCurrencyServicePort exchangeCurrencyServicePort;
    private final BookingDomainServicePort bookingDomainServicePort;

    @Override
    public Optional<Booking> find(UUID userId, UUID financialLedgerId, UUID bookingId) {
        return bookingServicePort.find(userId, financialLedgerId, bookingId);
    }

    @Override
    public Optional<Booking> create(UUID userId, UUID financialLedgerId, BookingAttributeData attributeData) {
        return bookingServicePort.create(userId, financialLedgerId, attributeData);
    }

    @Override
    public boolean exists(UUID userId, UUID financialLedgerId, UUID bookingId) {
        return bookingServicePort.exists(userId, financialLedgerId, bookingId);
    }

    @Override
    public boolean delete(UUID userId, UUID financialLedgerId, UUID bookingId) {
        return bookingServicePort.delete(userId, financialLedgerId, bookingId);
    }

    @Override
    public boolean referenceUser(UUID id, UUID financialLedgerId, UUID bookingId, UUID referenceUserId) {
        return bookingServicePort.referenceUser(id, financialLedgerId, bookingId, referenceUserId);
    }

    @Override
    public boolean deleteUserReference(UUID id, UUID financialLedgerId, UUID bookingId) {
        return bookingServicePort.delete(id, financialLedgerId, bookingId);
    }

    @Override
    public Optional<Booking> updateByAttributeData(Booking booking, BookingAttributeData attributeData) {
        return bookingDomainServicePort.updateByAttributeData(booking, attributeData);
    }

    @Override
    public boolean exchangeCurrencyOfBooking(UUID id, UUID financialLedgerId, UUID bookingId, CurrencyType targetCurrencyType) {
        return exchangeCurrencyServicePort.exchangeCurrencyOfBooking(id, financialLedgerId, bookingId, targetCurrencyType);
    }

}
