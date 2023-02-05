package de.dhbw.cleanproject.application.currency.exchange;

import java.util.Optional;

public interface CurrencyExchangeOfficeService {
    Optional<Double> getExchangeRate(CurrencyExchangeRequest request);
}
