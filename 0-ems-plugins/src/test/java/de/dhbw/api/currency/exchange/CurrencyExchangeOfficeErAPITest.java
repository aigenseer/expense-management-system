package de.dhbw.api.currency.exchange;

import de.dhbw.ems.abstractioncode.service.RequestService;
import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeRequest;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeResponse;
import de.dhbw.plugins.api.curreny.exchange.CurrencyExchangeOfficeErAPI;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CurrencyExchangeOfficeErAPITest {

    @Mock
    private RequestService requestUtils;

    @InjectMocks
    private CurrencyExchangeOfficeErAPI currencyExchangeOffice;

    @Test
    public void testSave() throws URISyntaxException, IOException {
        CurrencyExchangeResponse response = CurrencyExchangeResponse.builder().rate(1.5).localDate(LocalDate.now()).build();
        Instant instant = response.getLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

        JSONObject jo = new JSONObject(){{
            put("code", 200);
            put("rates", new JSONObject(){{
                put("EURUSD", new JSONObject(){{
                    put("rate", response.getRate());
                    put("timestamp", instant.getEpochSecond());
                }});
            }});
        }};

        URL url = new URIBuilder("https://www.freeforexapi.com/api/live?pairs=EURUSD").build().toURL();
        when(requestUtils.stream(url)).thenReturn(jo.toString());

        CurrencyExchangeRequest request = CurrencyExchangeRequest.builder().sourceCurrencyType(CurrencyType.EURO).targetCurrencyType(CurrencyType.DOLLAR).build();
        Optional<CurrencyExchangeResponse> optionResponse = currencyExchangeOffice.getExchangeRate(request);
        assertTrue(optionResponse.isPresent());
        assertEquals(response.getLocalDate(), optionResponse.get().getLocalDate());
        assertEquals(response.getRate(), optionResponse.get().getRate());

        verify(requestUtils).stream(url);
    }

}
