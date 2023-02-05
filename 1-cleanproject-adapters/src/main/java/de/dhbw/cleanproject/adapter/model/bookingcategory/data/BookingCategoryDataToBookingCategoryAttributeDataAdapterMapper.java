package de.dhbw.cleanproject.adapter.model.bookingcategory.data;

import de.dhbw.cleanproject.application.bookingcategory.BookingCategoryAttributeData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper implements Function<BookingCategoryData, BookingCategoryAttributeData> {

    @Override
    public BookingCategoryAttributeData apply(final BookingCategoryData data) {
        return map(data);
    }

    private BookingCategoryAttributeData map(final BookingCategoryData data) {
        return BookingCategoryAttributeData.builder().title(data.getTitle()).build();
    }
}
