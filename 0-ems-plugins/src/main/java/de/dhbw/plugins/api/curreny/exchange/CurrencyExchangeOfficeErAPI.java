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
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class CurrencyExchangeOfficeErAPI extends CurrencyExchangeOffice implements CurrencyExchangeOfficeAPI {

    private final RequestServiceHelper requestServiceHelper;

    public CurrencyExchangeOfficeErAPI(
            final EnvironmentServiceHelper environmentServiceHelper,
            final RequestServiceHelper requestServiceHelper
    ) {
        super("er-api", environmentServiceHelper);
        this.requestServiceHelper = requestServiceHelper;
    }

    @PostConstruct
    @Override
    protected void init(){
        super.init();
    }

    private Optional<URL> getUrl(CurrencyExchangeRequest request){
        if (getOptionalURIBuilder().isPresent()){
            if (getCurrencyTypeMapping().containsKey(request.getSourceCurrencyType())){
                String mapping = getCurrencyTypeMapping().get(request.getSourceCurrencyType());
                try {
                    URIBuilder uriBuilder = new URIBuilder(getOptionalURIBuilder().get().build());
                    URL url = uriBuilder.setPath(String.format("%s/%s", getOptionalURIBuilder().get().getPath(), mapping)).build().toURL();
                    return Optional.of(url);
                } catch (MalformedURLException|URISyntaxException ignore) {}
            }
        }
        return Optional.empty();
    }

    private Optional<CurrencyExchangeResponse> convertJSONtoResponse(CurrencyExchangeRequest request, JSONObject jsonObject){
        String sourceCurrencyTypeMapping = getCurrencyTypeMapping().get(request.getSourceCurrencyType());
        String targetCurrencyTypeMapping = getCurrencyTypeMapping().get(request.getTargetCurrencyType());

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
            String json = requestServiceHelper.stream(optionalURL.get());
            JSONObject jsonObject = new JSONObject(json);
            return convertJSONtoResponse(request, jsonObject);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
