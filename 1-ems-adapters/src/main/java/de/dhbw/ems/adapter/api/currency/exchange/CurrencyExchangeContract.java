package de.dhbw.ems.adapter.api.currency.exchange;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.adapter.model.customvalidatior.ValueOfEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class CurrencyExchangeContract{

    @NotEmpty(message = "The target currencyType is required.")
    @ValueOfEnum(enumClass = CurrencyType.class, message = "Target invalid currency type")
    private String targetCurrencyType;

}
