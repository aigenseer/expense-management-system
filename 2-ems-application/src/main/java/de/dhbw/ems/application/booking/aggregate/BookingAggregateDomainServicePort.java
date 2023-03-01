package de.dhbw.ems.application.booking.aggregate;

import de.dhbw.ems.application.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;

import java.util.Optional;

public interface BookingAggregateDomainServicePort {

    Optional<BookingAggregate> updateByAttributeData(BookingAggregate bookingAggregate, BookingAggregateAttributeData attributeData);
}
