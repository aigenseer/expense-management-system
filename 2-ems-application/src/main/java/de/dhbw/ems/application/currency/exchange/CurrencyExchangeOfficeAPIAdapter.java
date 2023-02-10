package de.dhbw.ems.application.currency.exchange;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CurrencyExchangeOfficeAPIAdapter {

    private final List<CurrencyExchangeOfficeAPI> currencyExchangeOfficeAPIS;

    public Optional<Double> getExchangeRate(CurrencyExchangeRequest request) {
        return currencyExchangeOfficeAPIS.parallelStream()
                        .map(currencyExchangeOfficeAPI -> currencyExchangeOfficeAPI.getExchangeRate(request))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                .max(Comparator.comparing(CurrencyExchangeResponse::getLocalDate))
                .map(CurrencyExchangeResponse::getRate);
    }
}
