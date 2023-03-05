package de.dhbw.ems.adapter.mapper.data.bookingcategory;

import de.dhbw.ems.application.domain.service.bookingcategory.entity.BookingCategoryAttributeData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingCategoryDataToAttributeDataMapper implements BookingCategoryDataToBookingCategoryAttributeDataAdapterMapper {

    @Override
    public BookingCategoryAttributeData apply(final IBookingCategoryData data) {
        return map(data);
    }

    private BookingCategoryAttributeData map(final IBookingCategoryData data) {
        return BookingCategoryAttributeData.builder().title(data.getTitle()).build();
    }
}
