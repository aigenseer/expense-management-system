package de.dhbw.cleanproject.application.currency.exchange;

import java.util.Optional;

public interface CurrencyExchangeOffice {
    Optional<Double> getExchangeRate(CurrencyExchangeRequest request);
}
