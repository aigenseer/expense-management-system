package de.dhbw.cleanproject.adapter.bookingcategory.data;

import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BookingCategoryDataToBookingCategoryMapper implements Function<BookingCategoryData, BookingCategory> {

    @Override
    public BookingCategory apply(final BookingCategoryData data) {
        return map(data);
    }

    private BookingCategory map(final BookingCategoryData data) {
        BookingCategory.BookingCategoryBuilder builder = BookingCategory.builder();
        builder.id(UUID.randomUUID());
        builder.title(data.getTitle());
        return builder.build();
    }

}
