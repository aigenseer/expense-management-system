package de.dhbw.ems.application.booking.aggregate;

import de.dhbw.ems.application.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.application.booking.entity.BookingDomainService;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregateRepository;
import de.dhbw.ems.domain.booking.entity.Booking;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Optional<BookingAggregate> createByAttributeData(User user, FinancialLedger financialLedger, BookingAggregateAttributeData attributeData){
        Optional<Booking> optionalBooking = bookingDomainService.createByAttributeData(attributeData);
        if (!optionalBooking.isPresent()) return Optional.empty();

        BookingAggregate bookingAggregate = BookingAggregate.builder()
                .id(UUID.randomUUID())
                .booking(optionalBooking.get())
                .bookingId(optionalBooking.get().getId())
                .creatorId(user.getId())
                .creator(user)
                .creationDate(LocalDate.now())
                .financialLedgerId(financialLedger.getId())
                .financialLedger(financialLedger)
                .build();
        return updateByAttributeData(bookingAggregate, attributeData);
    }

    public Optional<BookingAggregate> updateByAttributeData(BookingAggregate bookingAggregate, BookingAggregateAttributeData attributeData){
        Optional<Booking> optionalBooking = bookingDomainService.updateByAttributeData(bookingAggregate.getBooking(), attributeData);
        if (!optionalBooking.isPresent()) return Optional.empty();

        if (attributeData.getBookingCategory() != null){
            bookingAggregate.setCategory(attributeData.getBookingCategory());
        }
        bookingAggregate = save(bookingAggregate);
        return Optional.of(bookingAggregate);
    }

}
