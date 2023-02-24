package de.dhbw.ems.adapter.model.bookingcategory.preview;

import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import org.springframework.stereotype.Component;

@Component
public class BookingCategoryToPreviewModelMapper implements BookingCategoryAggregateToBookingCategoryPreviewModelAdapterMapper {

    @Override
    public BookingCategoryPreviewModel apply(final BookingCategoryAggregate categoryAggregate) {
        return map(categoryAggregate);
    }

    private BookingCategoryPreviewModel map(final BookingCategoryAggregate categoryAggregate) {
        return BookingCategoryPreviewModel.builder()
                .title(categoryAggregate.getBookingCategory().getTitle())
                .build();
    }
}
