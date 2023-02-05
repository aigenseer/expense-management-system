package de.dhbw.cleanproject.adapter.api.currency.exchange;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.cleanproject.application.currency.exchange.CurrencyExchangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CurrencyExchangeContractToCurrencyExchangeRequestAdapterMapper implements Function<CurrencyExchangeContract, CurrencyExchangeRequest> {

    @Override
    public CurrencyExchangeRequest apply(final CurrencyExchangeContract contract) {
        return map(contract);
    }

    private CurrencyExchangeRequest map(final CurrencyExchangeContract contract) {
        CurrencyType targetCurrencyType = CurrencyType.valueOf(contract.getTargetCurrencyType());
        return CurrencyExchangeRequest.builder().targetCurrencyType(targetCurrencyType).build();
    }

}
