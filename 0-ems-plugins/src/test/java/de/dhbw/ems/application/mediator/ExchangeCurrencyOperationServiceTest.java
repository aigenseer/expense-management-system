package de.dhbw.ems.application.mediator;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.application.booking.aggregate.BookingAggregateApplicationService;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeOfficeService;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeRequest;
import de.dhbw.ems.application.mediator.service.BookingOperationService;
import de.dhbw.ems.application.mediator.service.ExchangeCurrencyOperationService;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@DataJpaTest()
@ComponentScan("de.dhbw")
public class ExchangeCurrencyOperationServiceTest {

    @Autowired
    private BookingOperationService bookingOperationService;
    @Autowired
    private BookingAggregateApplicationService bookingAggregateApplicationService;

    private final UUID userId = UUID.fromString("12345678-1234-1234-a123-123456789001");
    private final UUID financialLedgerId = UUID.fromString("12345678-1234-1234-a123-123456789011");
    private final UUID bookingId = UUID.fromString("12345678-1234-1234-a123-123456789031");
    private BookingAggregate bookingAggregate;

    @Before
    public void setup(){
        Optional<BookingAggregate> optionalBooking =  bookingAggregateApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        bookingAggregate = optionalBooking.get();
    }

    @Test
    public void testExchangeCurrencyOfBooking(){
        CurrencyExchangeRequest currencyExchangeRequest = CurrencyExchangeRequest.builder().sourceCurrencyType(CurrencyType.EURO).targetCurrencyType(CurrencyType.DOLLAR).build();
        CurrencyExchangeOfficeService currencyExchangeOfficeService = mock(CurrencyExchangeOfficeService.class);
        Double factor = 1.5;
        when(currencyExchangeOfficeService.getExchangeRate(currencyExchangeRequest)).thenReturn(Optional.of(factor));

        Double expectedAmount = bookingAggregate.getBooking().getMoney().getAmount() * factor;
        expectedAmount = Math.round(expectedAmount*100.0)/100.0;

        ExchangeCurrencyOperationService service = Mockito.spy(new ExchangeCurrencyOperationService(bookingAggregateApplicationService, bookingOperationService, currencyExchangeOfficeService));

        boolean result = service.exchangeCurrencyOfBooking(userId, financialLedgerId, bookingId, CurrencyType.DOLLAR);
        assertTrue(result);

        Optional<BookingAggregate> optionalBooking = bookingAggregateApplicationService.findById(bookingId);
        assertTrue(optionalBooking.isPresent());
        assertEquals(CurrencyType.DOLLAR, optionalBooking.get().getBooking().getMoney().getCurrencyType());
        assertEquals(expectedAmount, optionalBooking.get().getBooking().getMoney().getAmount());
    }


}
