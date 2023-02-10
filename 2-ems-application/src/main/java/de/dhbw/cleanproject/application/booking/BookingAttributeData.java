package de.dhbw.cleanproject.application.booking;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.Money;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingAttributeData {
    private String title;
    private Money money;
    private BookingCategory bookingCategory;
}
