package de.dhbw.ems.application.currency.exchange;

import java.util.Optional;

public interface CurrencyExchangeOfficeAPI {
    Optional<CurrencyExchangeResponse> getExchangeRate(CurrencyExchangeRequest request);
}
