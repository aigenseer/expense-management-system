package de.dhbw.ems.adapter.model.bookingcategory.data;

import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingCategoryToModelMapper implements BookingCategoryToBookingCategoryModelAdapterMapper {

    @Override
    public BookingCategoryModel apply(final BookingCategory bookingCategory) {
        return map(bookingCategory);
    }

    private BookingCategoryModel map(final BookingCategory bookingCategory) {
        BookingCategoryModel.BookingCategoryModelBuilder builder = BookingCategoryModel.builder()
                .title(bookingCategory.getTitle());
        return builder.build();
    }
}
