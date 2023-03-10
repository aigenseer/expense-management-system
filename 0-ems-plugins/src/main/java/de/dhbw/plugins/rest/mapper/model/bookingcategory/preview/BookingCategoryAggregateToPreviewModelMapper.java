package de.dhbw.plugins.rest.mapper.model.bookingcategory.preview;

import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import org.springframework.stereotype.Component;

@Component
public class BookingCategoryAggregateToPreviewModelMapper implements BookingCategoryAggregateToBookingCategoryPreviewModelMapper {

    @Override
    public BookingCategoryPreviewModel apply(final BookingCategoryAggregate categoryAggregate) {
        return map(categoryAggregate);
    }

    private BookingCategoryPreviewModel map(final BookingCategoryAggregate categoryAggregate) {
        return BookingCategoryPreviewModel.builder()
                .title(categoryAggregate.getTitle())
                .build();
    }
}
