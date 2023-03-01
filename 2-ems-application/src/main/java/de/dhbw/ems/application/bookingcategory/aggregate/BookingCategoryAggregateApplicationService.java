package de.dhbw.ems.application.bookingcategory.aggregate;

import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;
import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryDomainService;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregateRepository;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingCategoryAggregateApplicationService implements BookingCategoryAggregateDomainService {

    private final BookingCategoryAggregateRepository repository;
    private final BookingCategoryDomainService bookingCategoryDomainService;

    @Override
    public BookingCategoryAggregate save(BookingCategoryAggregate bookingCategoryAggregate) {
        return repository.save(bookingCategoryAggregate);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<BookingCategoryAggregate> createByAttributeData(FinancialLedgerAggregate financialLedgerAggregate, BookingCategoryAttributeData data) {
        Optional<BookingCategory> optionalBookingCategory = bookingCategoryDomainService.createByAttributeData(data);
        if (!optionalBookingCategory.isPresent()) return Optional.empty();
        BookingCategoryAggregate bookingCategoryAggregate = BookingCategoryAggregate.builder()
                .id(UUID.randomUUID())
                .bookingCategoryId(optionalBookingCategory.get().getId())
                .bookingCategory(optionalBookingCategory.get())
                .financialLedgerAggregate(financialLedgerAggregate)
                .financialLedgerId(financialLedgerAggregate.getId())
                .build();
        return Optional.of(save(bookingCategoryAggregate));
    }

    @Override
    public Optional<BookingCategoryAggregate> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<BookingCategoryAggregate> updateByAttributeData(BookingCategoryAggregate bookingCategoryAggregate, BookingCategoryAttributeData data) {
        Optional<BookingCategory> optionalBookingCategory = bookingCategoryDomainService.updateByAttributeData(bookingCategoryAggregate.getBookingCategory(), data);
        if (!optionalBookingCategory.isPresent()) return Optional.empty();
        return findById(bookingCategoryAggregate.getId());
    }

}
