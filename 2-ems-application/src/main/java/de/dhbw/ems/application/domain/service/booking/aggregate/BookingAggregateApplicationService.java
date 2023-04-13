package de.dhbw.ems.application.domain.service.booking.aggregate;

import de.dhbw.ems.application.domain.service.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregateRepository;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedgerAggregate;
import de.dhbw.ems.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingAggregateApplicationService implements BookingAggregateDomainService {

    private final BookingAggregateRepository repository;

    public Optional<BookingAggregate> findById(UUID id) {
        return repository.findById(id);
    }

    public BookingAggregate save(BookingAggregate bookingAggregate) {
        return repository.save(bookingAggregate);
    }

    public List<BookingAggregate> findByCreatorId(UUID creatorId) {
        return repository.findByCreatorId(creatorId);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Optional<BookingAggregate> createByAttributeData(User user, FinancialLedgerAggregate financialLedgerAggregate, BookingAggregateAttributeData attributeData){
        BookingAggregate.BookingAggregateBuilder bookingAggregateBuilder = BookingAggregate.builder()
                .id(UUID.randomUUID())
                .title(attributeData.getTitle())
                .money(attributeData.getMoney())
                .creatorId(user.getId())
                .creator(user)
                .creationDate(LocalDate.now())
                .financialLedgerId(financialLedgerAggregate.getId())
                .financialLedgerAggregate(financialLedgerAggregate);

        if (attributeData.getBookingCategoryAggregate() != null){
            bookingAggregateBuilder.categoryAggregateId(attributeData.getBookingCategoryAggregate().getId());
            bookingAggregateBuilder.categoryAggregate(attributeData.getBookingCategoryAggregate());
        }
        return updateByAttributeData(bookingAggregateBuilder.build(), attributeData);
    }

    public Optional<BookingAggregate> updateByAttributeData(BookingAggregate bookingAggregate, BookingAggregateAttributeData attributeData){
        if (attributeData.getTitle() != null){
            bookingAggregate.setTitle(attributeData.getTitle());
        }
        if (attributeData.getMoney() != null){
            bookingAggregate.setMoney(attributeData.getMoney());
        }

        if (attributeData.getBookingCategoryAggregateActive() && attributeData.getBookingCategoryAggregate() != null){
            bookingAggregate.setCategoryAggregateId(attributeData.getBookingCategoryAggregate().getId());
            bookingAggregate.setCategoryAggregate(attributeData.getBookingCategoryAggregate());
        }else if (attributeData.getBookingCategoryAggregateActive()){
            bookingAggregate.setCategoryAggregateId(null);
            bookingAggregate.setCategoryAggregate(null);
        }

        return Optional.of(save(bookingAggregate));
    }

}
