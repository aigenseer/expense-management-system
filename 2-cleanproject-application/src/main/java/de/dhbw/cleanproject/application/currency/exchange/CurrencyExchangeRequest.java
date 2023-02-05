package de.dhbw.cleanproject.application.currency.exchange;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyExchangeRequest {
    private CurrencyType sourceCurrencyType;
    private CurrencyType targetCurrencyType;
}
