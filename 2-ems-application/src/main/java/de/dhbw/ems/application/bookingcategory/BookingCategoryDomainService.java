package de.dhbw.ems.application.bookingcategory;

import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryDomainService extends BookingCategoryDomainServicePort {

    BookingCategory save(BookingCategory bookingCategory);

    void deleteById(UUID id);

    Optional<BookingCategory> createByAttributeData(FinancialLedger financialLedger, BookingCategoryAttributeData data);

}
