package de.dhbw.ems.application.booking.data;

import de.dhbw.ems.abstractioncode.valueobject.money.Money;
import lombok.Data;

@Data
public class BookingAttributeData {
    private String title;
    private Money money;
}
