package de.dhbw.ems.application.domain.service.booking.aggregate;

import de.dhbw.ems.application.domain.service.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregate;
import de.dhbw.ems.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingAggregateDomainService extends BookingAggregateDomainServicePort {

    Optional<BookingAggregate> findById(UUID id);

    BookingAggregate save(BookingAggregate bookingAggregate);

    List<BookingAggregate> findByCreatorId(UUID id);

    void deleteById(UUID id);

    Optional<BookingAggregate> createByAttributeData(User user, FinancialLedgerAggregate financialLedgerAggregate, BookingAggregateAttributeData attributeData);

}
