package de.dhbw.cleanproject.application.currency.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeOfficeAPIAdapter implements CurrencyExchangeOffice {

    private final List<CurrencyExchangeOfficeAPI> currencyExchangeOfficeAPIS;

    @Override
    public Optional<Double> getExchangeRate(CurrencyExchangeRequest request) {
        return null;
    }
}
