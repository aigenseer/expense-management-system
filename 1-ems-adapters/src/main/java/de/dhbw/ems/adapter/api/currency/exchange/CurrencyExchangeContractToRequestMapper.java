package de.dhbw.ems.adapter.api.currency.exchange;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyExchangeContractToRequestMapper implements CurrencyExchangeContractToCurrencyExchangeRequestAdapterMapper {

    @Override
    public CurrencyExchangeRequest apply(final ICurrencyExchangeContract contract) {
        return map(contract);
    }

    private CurrencyExchangeRequest map(final ICurrencyExchangeContract contract) {
        CurrencyType targetCurrencyType = CurrencyType.valueOf(contract.getTargetCurrencyType());
        return CurrencyExchangeRequest.builder().targetCurrencyType(targetCurrencyType).build();
    }

}
