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
    public Optional<BookingAggregate> find(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId) {
        return bookingServicePort.find(userId, financialLedgerAggregateId, bookingAggregateId);
    }

    @Override
    public Optional<BookingAggregate> create(UUID userId, UUID financialLedgerAggregateId, BookingAggregateAttributeData attributeData) {
        return bookingServicePort.create(userId, financialLedgerAggregateId, attributeData);
    }

    @Override
    public boolean exists(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId) {
        return bookingServicePort.exists(userId, financialLedgerAggregateId, bookingAggregateId);
    }

    @Override
    public boolean delete(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId) {
        return bookingServicePort.delete(userId, financialLedgerAggregateId, bookingAggregateId);
    }

    @Override
    public boolean referenceUser(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId, UUID referenceUserId) {
        return bookingServicePort.referenceUser(userId, financialLedgerAggregateId, bookingAggregateId, referenceUserId);
    }

    @Override
    public boolean deleteUserReference(UUID userId, UUID financialLedgerAggregateId, UUID bookingAggregateId, UUID referenceUserId) {
        return bookingServicePort.deleteUserReference(userId, financialLedgerAggregateId, bookingAggregateId, referenceUserId);
    }

    @Override
    public Optional<BookingAggregate> updateByAttributeData(BookingAggregate bookingAggregate, BookingAggregateAttributeData attributeData) {
        return bookingAggregateDomainServicePort.updateByAttributeData(bookingAggregate, attributeData);
    }

    @Override
    public boolean exchangeCurrencyOfBooking(UUID id, UUID financialLedgerAggregateId, UUID bookingAggregateId, CurrencyType targetCurrencyType) {
        return exchangeCurrencyServicePort.exchangeCurrencyOfBooking(id, financialLedgerAggregateId, bookingAggregateId, targetCurrencyType);
    }

}
