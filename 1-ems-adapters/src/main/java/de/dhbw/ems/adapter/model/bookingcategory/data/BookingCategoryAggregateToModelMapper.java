package de.dhbw.ems.adapter.model.bookingcategory.data;

import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingCategoryAggregateToModelMapper implements BookingCategoryAggregateToBookingCategoryModelAdapterMapper {

    @Override
    public BookingCategoryModel apply(final BookingCategoryAggregate bookingCategoryAggregate) {
        return map(bookingCategoryAggregate);
    }

    private BookingCategoryModel map(final BookingCategoryAggregate bookingCategoryAggregate) {
        BookingCategoryModel.BookingCategoryModelBuilder builder = BookingCategoryModel.builder()
                .title(bookingCategoryAggregate.getBookingCategory().getTitle());
        return builder.build();
    }
}
