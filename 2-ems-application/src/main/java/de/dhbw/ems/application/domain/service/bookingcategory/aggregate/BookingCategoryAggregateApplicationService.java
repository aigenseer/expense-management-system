package de.dhbw.ems.application.domain.service.bookingcategory.aggregate;

import de.dhbw.ems.application.domain.service.bookingcategory.data.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregateRepository;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingCategoryAggregateApplicationService implements BookingCategoryAggregateDomainService {

    private final BookingCategoryAggregateRepository repository;

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
        BookingCategoryAggregate bookingCategoryAggregate = BookingCategoryAggregate.builder()
                .id(UUID.randomUUID())
                .financialLedgerAggregate(financialLedgerAggregate)
                .financialLedgerId(financialLedgerAggregate.getId())
                .build();
        return updateByAttributeData(bookingCategoryAggregate, data);
    }

    @Override
    public Optional<BookingCategoryAggregate> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<BookingCategoryAggregate> updateByAttributeData(BookingCategoryAggregate bookingCategoryAggregate, BookingCategoryAttributeData data) {
        bookingCategoryAggregate.setTitle(data.getTitle());
        return Optional.of(save(bookingCategoryAggregate));
    }

}
