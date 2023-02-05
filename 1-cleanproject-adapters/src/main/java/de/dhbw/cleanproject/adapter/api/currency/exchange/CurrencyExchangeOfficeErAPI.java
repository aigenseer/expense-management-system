package de.dhbw.cleanproject.adapter.api.currency.exchange;

import de.dhbw.cleanproject.abstractioncode.service.RequestService;
import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.cleanproject.application.currency.exchange.CurrencyExchangeOfficeAPI;
import de.dhbw.cleanproject.application.currency.exchange.CurrencyExchangeRequest;
import de.dhbw.cleanproject.application.currency.exchange.CurrencyExchangeResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
public class CurrencyExchangeOfficeErAPI implements CurrencyExchangeOfficeAPI {

    private URIBuilder uriBuilder;
    {
        try {
            uriBuilder = new URIBuilder("https://www.freeforexapi.com/api/live");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    private final RequestService requestService;

    private final Map<CurrencyType, String> currencyTypeMapping = new HashMap<CurrencyType, String>(){{
        put(CurrencyType.EURO, "EUR");
        put(CurrencyType.DOLLAR, "USD");
    }};

    private Optional<String> getPairsByRequest(CurrencyExchangeRequest request){
        if (currencyTypeMapping.containsKey(request.getSourceCurrencyType()) && currencyTypeMapping.containsKey(request.getTargetCurrencyType()) ){
            String pairs = String.format("%s%s", currencyTypeMapping.get(request.getSourceCurrencyType()), currencyTypeMapping.get(request.getTargetCurrencyType()));
            return Optional.of(pairs);
        }
        return Optional.empty();
    }

    private Optional<CurrencyExchangeResponse> convertJSONtoResponse(String pairs, JSONObject jsonObject){
        if (jsonObject.has("code") && jsonObject.get("code").equals(200) && jsonObject.has("rates")){
            jsonObject = jsonObject.getJSONObject("rates");
            if (jsonObject.has(pairs)){
                jsonObject = jsonObject.getJSONObject(pairs);
                if (jsonObject.has("rate") && jsonObject.has("timestamp")){
                    Double rate = jsonObject.getDouble("rate");
                    Instant instant = Instant.ofEpochSecond(jsonObject.getInt("timestamp"));
                    LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                    return Optional.of(CurrencyExchangeResponse.builder().rate(rate).localDate(date).build());
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CurrencyExchangeResponse> getExchangeRate(CurrencyExchangeRequest request) {
        uriBuilder.clearParameters();
        Optional<String> optionalPairs = getPairsByRequest(request);
        if(!optionalPairs.isPresent()) return Optional.empty();
        uriBuilder.addParameter("pairs", optionalPairs.get());
        try {
            URL url = uriBuilder.build().toURL();
            String json = requestService.stream(url);
            JSONObject jsonObject = new JSONObject(json);
            return convertJSONtoResponse(optionalPairs.get(), jsonObject);
        } catch (URISyntaxException | IOException ignored) {}
        return Optional.empty();
    }
}
