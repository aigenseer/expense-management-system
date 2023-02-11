package de.dhbw.ems.application.booking;

import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.booking.BookingRepository;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingApplicationService implements BookingDomainService {

    private final BookingRepository repository;

    public List<Booking> findAllWithFinancialLedgerId(UUID financialLedgerId) {
        return repository.findAllWithFinancialLedgerId(financialLedgerId);
    }

    public Optional<Booking> findById(UUID id) {
        return repository.findById(id);
    }

    public Booking save(Booking booking) {
        return repository.save(booking);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Optional<Booking> createByAttributeData(User user, FinancialLedger financialLedger, BookingAttributeData attributeData){
        Booking booking = Booking.builder()
                .id(UUID.randomUUID())
                .creator(user)
                .creationDate(LocalDate.now())
                .financialLedgerId(financialLedger.getId())
                .build();
        return updateByAttributeData(booking, attributeData);
    }

    public Optional<Booking> updateByAttributeData(Booking booking, BookingAttributeData attributeData){
        if (attributeData.getTitle() != null){
            booking.setTitle(attributeData.getTitle());
        }
        if (attributeData.getMoney() != null){
            booking.setMoney(attributeData.getMoney());
        }
        if (attributeData.getBookingCategory() != null){
            booking.setCategory(attributeData.getBookingCategory());
        }
        return Optional.of(save(booking));
    }

}
