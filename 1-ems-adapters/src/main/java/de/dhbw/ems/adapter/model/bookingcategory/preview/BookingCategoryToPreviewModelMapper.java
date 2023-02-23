package de.dhbw.ems.adapter.model.bookingcategory.preview;

import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import org.springframework.stereotype.Component;

@Component
public class BookingCategoryToPreviewModelMapper implements BookingCategoryToBookingCategoryPreviewModelAdapterMapper {

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
