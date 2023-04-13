package de.dhbw.plugins.api.curreny.exchange;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.plugins.utils.service.EnvironmentServiceHelper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.*;

@RequiredArgsConstructor
abstract class CurrencyExchangeOffice {

    private final String ENVIRONMENT_PATH_BASE;
    private final EnvironmentServiceHelper environmentServiceHelper;

    @Getter(AccessLevel.PROTECTED)
    private Optional<URIBuilder> optionalURIBuilder = Optional.empty();
    @Getter(AccessLevel.PROTECTED)
    private Map<CurrencyType, String> currencyTypeMapping = new EnumMap<>(CurrencyType.class);

    protected void init(){
        optionalURIBuilder = createUriBuilder(ENVIRONMENT_PATH_BASE);
        currencyTypeMapping = createCurrencyTypeMapping(ENVIRONMENT_PATH_BASE);
    }

    private Optional<URIBuilder> createUriBuilder(String basePath){
        Optional<String> optionalURL = environmentServiceHelper.getProperty(String.format("ems.currency-exchange-office.%s.url", basePath));
        if (optionalURL.isPresent()){
            try {
                return Optional.of(new URIBuilder(optionalURL.get()));
            } catch (URISyntaxException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private Map<CurrencyType, String> createCurrencyTypeMapping(String basePath){
        Map<CurrencyType, String> mapping = new EnumMap<>(CurrencyType.class);
        for (CurrencyType currencyType : CurrencyType.values()) {
            Optional<String> optionalValue = environmentServiceHelper.getProperty(String.format("ems.currency-exchange-office.%s.currency-type-mapping.%s", basePath, currencyType.toString().toLowerCase(Locale.ROOT)));
            optionalValue.ifPresent(s -> mapping.put(currencyType, s));
        }
        return mapping;
    }
}
