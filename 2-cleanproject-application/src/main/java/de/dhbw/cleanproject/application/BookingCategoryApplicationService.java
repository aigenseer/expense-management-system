package de.dhbw.cleanproject.application;

import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingCategoryApplicationService {

    private final BookingCategoryRepository repository;

    public Optional<BookingCategory> findById(UUID id) {
        return repository.findById(id);
    }

    public BookingCategory save(BookingCategory bookingCategory) {
        return repository.save(bookingCategory);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
