package de.dhbw.cleanproject.application.booking;

import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.booking.BookingRepository;
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

}
