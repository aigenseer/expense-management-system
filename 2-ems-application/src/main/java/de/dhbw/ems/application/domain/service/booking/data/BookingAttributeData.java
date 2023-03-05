package de.dhbw.ems.application.domain.service.booking.data;

import de.dhbw.ems.abstractioncode.valueobject.money.Money;
import lombok.Data;

@Data
public class BookingAttributeData {
    private String title;
    private Money money;
}
