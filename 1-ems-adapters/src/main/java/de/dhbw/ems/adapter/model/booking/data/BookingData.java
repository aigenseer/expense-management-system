package de.dhbw.ems.adapter.model.booking.data;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.adapter.model.customvalidatior.ValueOfEnum;
import de.dhbw.ems.adapter.model.customvalidatior.ValueOfUUID;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class BookingData implements IBookingUnsafeData {

    @NotEmpty(message = "The title is required.")
    @Size(min = 2, max = 100, message = "The length of full title must be between 2 and 100 characters.")
    private String title;

    @DecimalMin(value = "0.0", message = "The amount is not valid.")
    private Double amount;

    @NotEmpty(message = "The currencyType is required.")
    @ValueOfEnum(enumClass = CurrencyType.class, message = "Invalid currency type")
    private String currencyType;

    @ValueOfUUID(message = "The bookingCategoryId is invalid UUID.")
    private String bookingCategoryAggregateId;

}