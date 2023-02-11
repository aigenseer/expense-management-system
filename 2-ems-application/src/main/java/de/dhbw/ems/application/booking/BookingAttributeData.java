package de.dhbw.ems.application.booking;

import de.dhbw.ems.abstractioncode.valueobject.money.Money;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingAttributeData {
    private String title;
    private Money money;
    private BookingCategory bookingCategory;
}
