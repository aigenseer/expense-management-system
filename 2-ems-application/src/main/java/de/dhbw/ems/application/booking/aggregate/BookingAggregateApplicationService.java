package de.dhbw.ems.application.booking.aggregate;

import de.dhbw.ems.application.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.application.booking.entity.BookingDomainService;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregateRepository;
import de.dhbw.ems.domain.booking.entity.Booking;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
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
    private final BookingDomainService bookingDomainService;

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
        Optional<Booking> optionalBooking = bookingDomainService.createByAttributeData(attributeData);
        if (!optionalBooking.isPresent()) return Optional.empty();

        BookingAggregate bookingAggregate = BookingAggregate.builder()
                .id(UUID.randomUUID())
                .booking(optionalBooking.get())
                .bookingId(optionalBooking.get().getId())
                .creatorId(user.getId())
                .creator(user)
                .creationDate(LocalDate.now())
                .financialLedgerId(financialLedgerAggregate.getId())
                .financialLedgerAggregate(financialLedgerAggregate)
                .build();
        return updateByAttributeData(bookingAggregate, attributeData);
    }

    public Optional<BookingAggregate> updateByAttributeData(BookingAggregate bookingAggregate, BookingAggregateAttributeData attributeData){
        Optional<Booking> optionalBooking = bookingDomainService.updateByAttributeData(bookingAggregate.getBooking(), attributeData);
        if (!optionalBooking.isPresent()) return Optional.empty();

        if (attributeData.getBookingCategoryAggregate() != null){
            bookingAggregate.setCategoryAggregate(attributeData.getBookingCategoryAggregate());
        }
        bookingAggregate = save(bookingAggregate);
        return Optional.of(bookingAggregate);
    }

}
