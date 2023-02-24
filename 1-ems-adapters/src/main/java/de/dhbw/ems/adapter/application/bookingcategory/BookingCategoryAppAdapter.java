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
    public Optional<BookingCategoryAggregate> find(UUID id, UUID financialLedgerId, UUID bookingCategoryAggregateId) {
        return bookingCategoryServicePort.find(id, financialLedgerId, bookingCategoryAggregateId);
    }

    @Override
    public boolean exists(UUID id, UUID financialLedgerId, UUID bookingCategoryAggregateId) {
        return bookingCategoryServicePort.exists(id, financialLedgerId, bookingCategoryAggregateId);
    }

    @Override
    public boolean delete(UUID id, UUID financialLedgerId, UUID bookingCategoryAggregateId) {
        return bookingCategoryServicePort.delete(id, financialLedgerId, bookingCategoryAggregateId);
    }

    @Override
    public Optional<BookingCategoryAggregate> create(UUID id, UUID financialLedgerId, BookingCategoryAttributeData attributeData) {
        return bookingCategoryServicePort.create(id, financialLedgerId, attributeData);
    }

    @Override
    public Optional<BookingCategoryAggregate> updateByAttributeData(BookingCategoryAggregate bookingCategoryAggregate, BookingCategoryAttributeData data) {
        return bookingCategoryDomainServicePort.updateByAttributeData(bookingCategoryAggregate, data);
    }

}
