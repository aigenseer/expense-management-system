package de.dhbw.plugins.api.curreny.exchange;

import de.dhbw.ems.application.currency.exchange.CurrencyExchangeOfficeAPI;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeRequest;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeResponse;
import de.dhbw.plugins.utils.service.EnvironmentServiceHelper;
import de.dhbw.plugins.utils.service.RequestServiceHelper;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class CurrencyExchangeOfficeFreeForexAPI extends CurrencyExchangeOffice implements CurrencyExchangeOfficeAPI {

    private final RequestServiceHelper requestServiceHelper;

    public CurrencyExchangeOfficeFreeForexAPI(
            final EnvironmentServiceHelper environmentServiceHelper,
            final RequestServiceHelper requestServiceHelper
    ) {
        super("freeforexapi", environmentServiceHelper);
        this.requestServiceHelper = requestServiceHelper;
    }

    @PostConstruct
    @Override
    protected void init(){
        super.init();
    }

    private Optional<String> getPairsByRequest(CurrencyExchangeRequest request){
        if (getCurrencyTypeMapping().containsKey(request.getSourceCurrencyType()) && getCurrencyTypeMapping().containsKey(request.getTargetCurrencyType()) ){
            String pairs = String.format("%s%s", getCurrencyTypeMapping().get(request.getSourceCurrencyType()), getCurrencyTypeMapping().get(request.getTargetCurrencyType()));
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
        if (getOptionalURIBuilder().isPresent()){
            URIBuilder uriBuilder = getOptionalURIBuilder().get();
            uriBuilder.clearParameters();
            Optional<String> optionalPairs = getPairsByRequest(request);
            if(!optionalPairs.isPresent()) return Optional.empty();
            uriBuilder.addParameter("pairs", optionalPairs.get());
            try {
                URL url = uriBuilder.build().toURL();
                String json = requestServiceHelper.stream(url);
                JSONObject jsonObject = new JSONObject(json);
                return convertJSONtoResponse(optionalPairs.get(), jsonObject);
            } catch (URISyntaxException | IOException ignored) {}
        }
        return Optional.empty();
    }
}
