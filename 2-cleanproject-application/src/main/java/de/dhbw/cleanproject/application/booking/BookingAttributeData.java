package de.dhbw.cleanproject.application.booking;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.Money;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BookingAttributeData {
    private String title;
    private Money money;
    private UUID financialLedgerId;
}
