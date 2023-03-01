package de.dhbw.ems.application.booking.entity;

import de.dhbw.ems.application.booking.data.BookingAttributeData;
import de.dhbw.ems.domain.booking.entity.Booking;
import de.dhbw.ems.domain.booking.entity.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingApplicationService implements BookingDomainService {

    private final BookingRepository repository;

    public Optional<Booking> findById(UUID id) {
        return repository.findById(id);
    }

    public Booking save(Booking booking) {
        return repository.save(booking);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Optional<Booking> createByAttributeData(BookingAttributeData attributeData){
        Booking booking = Booking.builder()
                .id(UUID.randomUUID())
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
        return Optional.of(save(booking));
    }

}
