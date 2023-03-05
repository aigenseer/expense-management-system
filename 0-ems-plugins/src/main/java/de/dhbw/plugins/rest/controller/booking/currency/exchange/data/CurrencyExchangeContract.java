package de.dhbw.plugins.rest.controller.booking.currency.exchange.data;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.adapter.api.currency.exchange.ICurrencyExchangeContract;
import de.dhbw.plugins.rest.customvalidatior.ValueOfEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class CurrencyExchangeContract implements ICurrencyExchangeContract {

    @NotEmpty(message = "The target currencyType is required.")
    @ValueOfEnum(enumClass = CurrencyType.class, message = "Target invalid currency type")
    private String targetCurrencyType;

}
