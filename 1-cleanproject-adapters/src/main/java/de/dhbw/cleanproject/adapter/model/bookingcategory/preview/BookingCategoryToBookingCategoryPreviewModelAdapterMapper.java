package de.dhbw.cleanproject.adapter.model.bookingcategory.preview;

import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BookingCategoryToBookingCategoryPreviewModelAdapterMapper implements Function<BookingCategory, BookingCategoryPreviewModel> {

    @Override
    public BookingCategoryPreviewModel apply(final BookingCategory category) {
        return map(category);
    }

    private BookingCategoryPreviewModel map(final BookingCategory category) {
        return BookingCategoryPreviewModel.builder()
                .title(category.getTitle())
                .build();
    }
}
