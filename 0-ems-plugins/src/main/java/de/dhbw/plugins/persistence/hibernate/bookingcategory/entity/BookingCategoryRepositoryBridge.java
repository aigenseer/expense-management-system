package de.dhbw.plugins.persistence.hibernate.bookingcategory.entity;

import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class BookingCategoryRepositoryBridge implements BookingCategoryRepository {

    private final SpringDataBookingCategoryRepository repository;

    @Override
    public Optional<BookingCategory> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public BookingCategory save(BookingCategory bookingCategory) {
        return repository.save(bookingCategory);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
