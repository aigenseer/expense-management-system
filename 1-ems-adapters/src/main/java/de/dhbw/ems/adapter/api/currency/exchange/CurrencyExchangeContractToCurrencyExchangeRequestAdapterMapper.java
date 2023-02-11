package de.dhbw.ems.adapter.api.currency.exchange;

import de.dhbw.ems.application.currency.exchange.CurrencyExchangeRequest;

import java.util.function.Function;

public interface CurrencyExchangeContractToCurrencyExchangeRequestAdapterMapper extends Function<CurrencyExchangeContract, CurrencyExchangeRequest> {
}
