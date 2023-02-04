package de.dhbw.cleanproject.application.booking;

import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.booking.BookingRepository;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingApplicationService {

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

    public Optional<Booking> create(User user, FinancialLedger financialLedger, BookingAttributeData attributeData){
        Booking booking = Booking.builder()
                .id(UUID.randomUUID())
                .user(user)
                .financialLedger(financialLedger)
                .build();
        return update(booking, attributeData);
    }

    public Optional<Booking> update(Booking booking, BookingAttributeData attributeData){
        if (attributeData.getTitle() != null){
            booking.setTitle(attributeData.getTitle());
        }
        if (attributeData.getMoney() != null){
            booking.setMoney(attributeData.getMoney());
        }
        if (attributeData.getFinancialLedgerId() != null){
            booking.setFinancialLedgerId(attributeData.getFinancialLedgerId());
        }
        return Optional.of(save(booking));
    }

}
