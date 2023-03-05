package de.dhbw.ems.application.domain.service.bookingcategory.entity;

import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingCategoryApplicationService implements BookingCategoryDomainService {

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

    public Optional<BookingCategory> createByAttributeData(BookingCategoryAttributeData data){
        BookingCategory bookingCategory = BookingCategory.builder().id(UUID.randomUUID()).build();
        return updateByAttributeData(bookingCategory, data);
    }

    public Optional<BookingCategory> updateByAttributeData(BookingCategory bookingCategory, BookingCategoryAttributeData data){
        bookingCategory.setTitle(data.getTitle());
        return Optional.of(save(bookingCategory));
    }

}
