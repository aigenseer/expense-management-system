package de.dhbw.ems.adapter.application.booking;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.application.domain.service.booking.aggregate.BookingAggregateDomainServicePort;
import de.dhbw.ems.application.domain.service.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.application.mediator.service.impl.BookingServicePort;
import de.dhbw.ems.application.mediator.service.impl.ExchangeCurrencyServicePort;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingAppAdapter implements BookingApplicationAdapter {

    private final BookingServicePort bookingServicePort;
    private final ExchangeCurrencyServicePort exchangeCurrencyServicePort;
    private final BookingAggregateDomainServicePort bookingAggregateDomainServicePort;

    @Override
    public Optional<BookingAggregate> find(UUID userId, UUID financialLedgerId, UUID bookingAggregateId) {
        return bookingServicePort.find(userId, financialLedgerId, bookingAggregateId);
    }

    @Override
    public Optional<BookingAggregate> create(UUID userId, UUID financialLedgerId, BookingAggregateAttributeData attributeData) {
        return bookingServicePort.create(userId, financialLedgerId, attributeData);
    }

    @Override
    public boolean exists(UUID userId, UUID financialLedgerId, UUID bookingAggregateId) {
        return bookingServicePort.exists(userId, financialLedgerId, bookingAggregateId);
    }

    @Override
    public boolean delete(UUID userId, UUID financialLedgerId, UUID bookingAggregateId) {
        return bookingServicePort.delete(userId, financialLedgerId, bookingAggregateId);
    }

    @Override
    public boolean referenceUser(UUID userId, UUID financialLedgerId, UUID bookingAggregateId, UUID referenceUserId) {
        return bookingServicePort.referenceUser(userId, financialLedgerId, bookingAggregateId, referenceUserId);
    }

    @Override
    public boolean deleteUserReference(UUID userId, UUID financialLedgerId, UUID bookingAggregateId, UUID referenceUserId) {
        return bookingServicePort.deleteUserReference(userId, financialLedgerId, bookingAggregateId, referenceUserId);
    }

    @Override
    public Optional<BookingAggregate> updateByAttributeData(BookingAggregate bookingAggregate, BookingAggregateAttributeData attributeData) {
        return bookingAggregateDomainServicePort.updateByAttributeData(bookingAggregate, attributeData);
    }

    @Override
    public boolean exchangeCurrencyOfBooking(UUID id, UUID financialLedgerId, UUID bookingAggregateId, CurrencyType targetCurrencyType) {
        return exchangeCurrencyServicePort.exchangeCurrencyOfBooking(id, financialLedgerId, bookingAggregateId, targetCurrencyType);
    }

}
