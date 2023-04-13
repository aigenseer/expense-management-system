package de.dhbw.ems.application.domain.service.bookingcategory.aggregate;

import de.dhbw.ems.application.domain.service.bookingcategory.data.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryAggregateDomainService extends BookingCategoryDomainServicePort {

    BookingCategoryAggregate save(BookingCategoryAggregate bookingCategoryAggregate);

    void deleteById(UUID id);

    Optional<BookingCategoryAggregate> createByAttributeData(FinancialLedger financialLedger, BookingCategoryAttributeData data);

}
