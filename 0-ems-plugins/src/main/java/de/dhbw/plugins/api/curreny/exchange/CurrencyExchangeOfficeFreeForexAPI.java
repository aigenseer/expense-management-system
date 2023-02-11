package de.dhbw.plugins.api.curreny.exchange;

import de.dhbw.ems.abstractioncode.service.RequestService;
import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeOfficeAPI;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeRequest;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeOfficeFreeForexAPI implements CurrencyExchangeOfficeAPI {

    private URIBuilder uriBuilder;
    {
        try {
            uriBuilder = new URIBuilder("https://open.er-api.com/v6/latest");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    private final RequestService requestService;

    private final Map<CurrencyType, String> currencyTypeMapping = new HashMap<CurrencyType, String>(){{
        put(CurrencyType.EURO, "EUR");
        put(CurrencyType.DOLLAR, "USD");
    }};

    private Optional<URL> getUrl(CurrencyExchangeRequest request){
        if (currencyTypeMapping.containsKey(request.getSourceCurrencyType())){
            String mapping = currencyTypeMapping.get(request.getSourceCurrencyType());
            try {
                URL url = uriBuilder.setPath(String.format("%s/%s", uriBuilder.getPath(), mapping)).build().toURL();
                return Optional.of(url);
            } catch (MalformedURLException|URISyntaxException ignore) {}
        }
        return Optional.empty();
    }

    private Optional<CurrencyExchangeResponse> convertJSONtoResponse(CurrencyExchangeRequest request, JSONObject jsonObject){
        String sourceCurrencyTypeMapping = currencyTypeMapping.get(request.getSourceCurrencyType());
        String targetCurrencyTypeMapping = currencyTypeMapping.get(request.getTargetCurrencyType());

        if (jsonObject.has("result")
                && jsonObject.getString("result").equals("success")
                && jsonObject.has("rates")
                && jsonObject.getString("base_code").equals(sourceCurrencyTypeMapping)
                && jsonObject.has("time_last_update_unix")){

            Instant instant = Instant.ofEpochSecond(jsonObject.getInt("time_last_update_unix"));
            LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            jsonObject = jsonObject.getJSONObject("rates");
            if (jsonObject.has(targetCurrencyTypeMapping)){
                Double rate = jsonObject.getDouble(targetCurrencyTypeMapping);
                return Optional.of(CurrencyExchangeResponse.builder().rate(rate).localDate(date).build());
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CurrencyExchangeResponse> getExchangeRate(CurrencyExchangeRequest request) {
        Optional<URL> optionalURL = getUrl(request);
        if(!optionalURL.isPresent()) return Optional.empty();
        try {
            String json = requestService.stream(optionalURL.get());
            JSONObject jsonObject = new JSONObject(json);
            return convertJSONtoResponse(request, jsonObject);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
