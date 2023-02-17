package de.dhbw.plugins.api.currency.exchange;

import de.dhbw.plugins.api.curreny.exchange.CurrencyExchangeOfficeFreeForexAPI;
import de.dhbw.plugins.utils.service.EnvironmentServiceHelper;
import de.dhbw.plugins.utils.service.RequestService;
import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeRequest;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeResponse;
import de.dhbw.plugins.api.curreny.exchange.CurrencyExchangeOfficeErAPI;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    @Mock
    private EnvironmentServiceHelper environmentServiceHelper;

    @InjectMocks
    private CurrencyExchangeOfficeErAPI currencyExchangeOffice;


    @Before
    public void setup() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(environmentServiceHelper.getProperty("ems.currency-exchange-office.er-api.url"))
                .thenReturn(Optional.of("https://open.er-api.com/v6/latest"));
        when(environmentServiceHelper.getProperty("ems.currency-exchange-office.er-api.currency-type-mapping.euro"))
                .thenReturn(Optional.of("EUR"));
        when(environmentServiceHelper.getProperty("ems.currency-exchange-office.er-api.currency-type-mapping.dollar"))
                .thenReturn(Optional.of("USD"));

        Method privateMethod = CurrencyExchangeOfficeErAPI.class.getDeclaredMethod("init");
        privateMethod.setAccessible(true);
        privateMethod.invoke(currencyExchangeOffice);
    }

    @Test
    public void testSave() throws URISyntaxException, IOException {
        CurrencyExchangeResponse response = CurrencyExchangeResponse.builder().rate(1.5).localDate(LocalDate.now()).build();
        Instant instant = response.getLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

        JSONObject jo = new JSONObject(){{
            put("result", "success");
            put("base_code", "USD");
            put("time_last_update_unix", instant.getEpochSecond());
            put("rates", new JSONObject(){{
                put("EUR", response.getRate());
            }});
        }};

        URL url = new URIBuilder("https://open.er-api.com/v6/latest/USD").build().toURL();
        when(requestUtils.stream(url)).thenReturn(jo.toString());
        String x = jo.toString();

        CurrencyExchangeRequest request = CurrencyExchangeRequest.builder().sourceCurrencyType(CurrencyType.DOLLAR).targetCurrencyType(CurrencyType.EURO).build();
        Optional<CurrencyExchangeResponse> optionResponse = currencyExchangeOffice.getExchangeRate(request);
        assertTrue(optionResponse.isPresent());
        assertEquals(response.getLocalDate(), optionResponse.get().getLocalDate());
        assertEquals(response.getRate(), optionResponse.get().getRate());

        verify(requestUtils).stream(url);
    }

}
