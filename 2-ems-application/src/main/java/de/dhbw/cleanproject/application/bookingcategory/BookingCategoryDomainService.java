package de.dhbw.cleanproject.application.bookingcategory;

import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryDomainService {

    Optional<BookingCategory> findById(UUID id);

    BookingCategory save(BookingCategory bookingCategory);

    void deleteById(UUID id);

    Optional<BookingCategory> createByAttributeData(FinancialLedger financialLedger, BookingCategoryAttributeData data);

    Optional<BookingCategory> updateByAttributeData(BookingCategory bookingCategory, BookingCategoryAttributeData data);
}
