package de.dhbw.ems.application.bookingcategory;

import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.ems.domain.bookingcategory.BookingCategoryRepository;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingCategoryApplicationService implements BookingCategoryDomainServicePort {

    private final BookingCategoryRepository repository;

    public Optional<BookingCategory> findById(UUID id) {
        return repository.findById(id);
    }

    public BookingCategory save(BookingCategory bookingCategory) {
        return repository.save(bookingCategory);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Optional<BookingCategory> createByAttributeData(FinancialLedger financialLedger, BookingCategoryAttributeData data){
        BookingCategory bookingCategory = BookingCategory.builder().id(UUID.randomUUID()).financialLedger(financialLedger).build();
        return updateByAttributeData(bookingCategory, data);
    }

    public Optional<BookingCategory> updateByAttributeData(BookingCategory bookingCategory, BookingCategoryAttributeData data){
        bookingCategory.setTitle(data.getTitle());
        return Optional.of(save(bookingCategory));
    }

}
