package de.dhbw.ems.adapter.model.bookingcategory.data;

import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

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