package de.dhbw.ems.application.currency.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeOffice implements CurrencyExchangeOfficeService {

    private final List<CurrencyExchangeOfficeAPI> currencyExchangeOfficeAPIS;

    @Override
    public Optional<Double> getExchangeRate(CurrencyExchangeRequest request) {
        return new CurrencyExchangeOfficeAPIAdapter(currencyExchangeOfficeAPIS).getExchangeRate(request);
    }

}
