package de.dhbw.plugins.rest.controller.booking.data;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.adapter.mapper.data.booking.IBookingUnsafeData;
import de.dhbw.plugins.rest.customvalidatior.ValueOfEnum;
import de.dhbw.plugins.rest.customvalidatior.ValueOfUUID;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

@Data
public class BookingUpdateData implements IBookingUnsafeData {

    @Size(min = 2, max = 100, message = "The length of full title must be between 2 and 100 characters.")
    private String title;

    @DecimalMin("0.0")
    private Double amount;

    @ValueOfEnum(enumClass = CurrencyType.class, message = "Invalid currency type")
    private String currencyType;

    @ValueOfUUID(message = "The bookingCategoryId is invalid UUID.")
    private String bookingCategoryAggregateId;

}