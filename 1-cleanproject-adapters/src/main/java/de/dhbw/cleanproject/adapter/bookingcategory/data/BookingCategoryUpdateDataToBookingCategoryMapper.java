package de.dhbw.cleanproject.adapter.bookingcategory.data;

import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BookingCategoryUpdateDataToBookingCategoryMapper implements Function<Pair<BookingCategory, BookingCategoryData>, BookingCategory> {

    private final BookingCategoryDataToBookingCategoryMapper bookingCategoryDataToBookingCategoryMapper;

    @Override
    public BookingCategory apply(final Pair<BookingCategory, BookingCategoryData> data) {
        return map(data);
    }

    private BookingCategory map(final Pair<BookingCategory, BookingCategoryData> pair) {
        BookingCategory bookingCategory = pair.getValue0();
        BookingCategory.BookingCategoryBuilder builder = BookingCategory.builder();
        BookingCategory updateBookingCategory = bookingCategoryDataToBookingCategoryMapper.apply(pair.getValue1());

        builder.id(bookingCategory.getId());
        builder.title(updateBookingCategory.getTitle());
        builder.bookings(bookingCategory.getBookings());

        return builder.build();
    }

}
