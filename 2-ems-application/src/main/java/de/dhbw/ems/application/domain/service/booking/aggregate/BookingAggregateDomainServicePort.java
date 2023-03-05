package de.dhbw.ems.application.domain.service.booking.aggregate;

import de.dhbw.ems.application.domain.service.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;

import java.util.Optional;

public interface BookingAggregateDomainServicePort {

    Optional<BookingAggregate> updateByAttributeData(BookingAggregate bookingAggregate, BookingAggregateAttributeData attributeData);
}
