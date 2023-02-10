package de.dhbw.ems.application.currency.exchange;

import java.util.Optional;

public interface CurrencyExchangeOfficeService {
    Optional<Double> getExchangeRate(CurrencyExchangeRequest request);
}
