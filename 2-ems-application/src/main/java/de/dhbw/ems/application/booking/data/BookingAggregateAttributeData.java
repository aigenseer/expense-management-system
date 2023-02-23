package de.dhbw.ems.application.booking.data;

import de.dhbw.ems.abstractioncode.valueobject.money.Money;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingAggregateAttributeData extends BookingAttributeData {
    private String title;
    private Money money;
    private BookingCategory bookingCategory;
}
