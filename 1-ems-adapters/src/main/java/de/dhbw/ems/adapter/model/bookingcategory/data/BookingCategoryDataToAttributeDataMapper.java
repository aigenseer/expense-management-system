package de.dhbw.ems.adapter.model.bookingcategory.data;

import de.dhbw.ems.application.bookingcategory.entity.BookingCategoryAttributeData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingCategoryDataToAttributeDataMapper implements BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper {

    @Override
    public BookingCategoryAttributeData apply(final BookingCategoryData data) {
        return map(data);
    }

    private BookingCategoryAttributeData map(final BookingCategoryData data) {
        return BookingCategoryAttributeData.builder().title(data.getTitle()).build();
    }
}
