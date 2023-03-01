package de.dhbw.ems.adapter.application.bookingcategory;

import de.dhbw.ems.application.bookingcategory.aggregate.BookingCategoryDomainServicePort;
import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.application.mediator.service.impl.BookingCategoryServicePort;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingCategoryAppAdapter implements BookingCategoryApplicationAdapter {

    private final BookingCategoryServicePort bookingCategoryServicePort;
    private final BookingCategoryDomainServicePort bookingCategoryDomainServicePort;

    @Override
    public Optional<BookingCategoryAggregate> find(UUID id, UUID financialLedgerAggregateId, UUID bookingCategoryAggregateId) {
        return bookingCategoryServicePort.find(id, financialLedgerAggregateId, bookingCategoryAggregateId);
    }

    @Override
    public boolean exists(UUID id, UUID financialLedgerAggregateId, UUID bookingCategoryAggregateId) {
        return bookingCategoryServicePort.exists(id, financialLedgerAggregateId, bookingCategoryAggregateId);
    }

    @Override
    public boolean delete(UUID id, UUID financialLedgerAggregateId, UUID bookingCategoryAggregateId) {
        return bookingCategoryServicePort.delete(id, financialLedgerAggregateId, bookingCategoryAggregateId);
    }

    @Override
    public Optional<BookingCategoryAggregate> create(UUID id, UUID financialLedgerAggregateId, BookingCategoryAttributeData attributeData) {
        return bookingCategoryServicePort.create(id, financialLedgerAggregateId, attributeData);
    }

    @Override
    public Optional<BookingCategoryAggregate> updateByAttributeData(BookingCategoryAggregate bookingCategoryAggregate, BookingCategoryAttributeData data) {
        return bookingCategoryDomainServicePort.updateByAttributeData(bookingCategoryAggregate, data);
    }

}
