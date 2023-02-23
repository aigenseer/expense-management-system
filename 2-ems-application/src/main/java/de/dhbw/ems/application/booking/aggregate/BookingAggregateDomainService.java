package de.dhbw.ems.application.booking.aggregate;

import de.dhbw.ems.application.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;

import java.util.Optional;
import java.util.UUID;

public interface BookingAggregateDomainService extends BookingAggregateDomainServicePort {

    Optional<BookingAggregate> findById(UUID id);

    BookingAggregate save(BookingAggregate bookingAggregate);

    void deleteById(UUID id);

    Optional<BookingAggregate> createByAttributeData(User user, FinancialLedger financialLedger, BookingAggregateAttributeData attributeData);

}
