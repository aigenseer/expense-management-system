package de.dhbw.cleanproject.application.currency.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeOfficeAPIAdapter implements CurrencyExchangeOffice {

    private final List<CurrencyExchangeOfficeAPI> currencyExchangeOfficeAPIS;

    @Override
    public Optional<Double> getExchangeRate(CurrencyExchangeRequest request) {
        return currencyExchangeOfficeAPIS.parallelStream()
                        .map(currencyExchangeOfficeAPI -> currencyExchangeOfficeAPI.getExchangeRate(request))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                .max(Comparator.comparing(CurrencyExchangeResponse::getLocalDate))
                .map(CurrencyExchangeResponse::getRate);
    }
}
