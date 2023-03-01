package de.dhbw.ems.application.bookingcategory.aggregate;

import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryAggregateDomainService extends BookingCategoryDomainServicePort {

    BookingCategoryAggregate save(BookingCategoryAggregate bookingCategoryAggregate);

    void deleteById(UUID id);

    Optional<BookingCategoryAggregate> createByAttributeData(FinancialLedgerAggregate financialLedgerAggregate, BookingCategoryAttributeData data);

}
