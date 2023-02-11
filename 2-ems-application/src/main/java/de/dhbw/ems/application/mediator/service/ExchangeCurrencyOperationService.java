package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.abstractioncode.valueobject.money.Money;
import de.dhbw.ems.application.booking.BookingApplicationService;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeOfficeService;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeRequest;
import de.dhbw.ems.application.mediator.service.impl.ExchangeCurrencyService;
import de.dhbw.ems.domain.booking.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExchangeCurrencyOperationService implements ExchangeCurrencyService {

    private final BookingApplicationService bookingApplicationService;
    private final BookingOperationService bookingOperationService;
    private final CurrencyExchangeOfficeService currencyExchangeOfficeService;

    public boolean exchangeCurrencyOfBooking(UUID id, UUID financialLedgerId, UUID bookingId, CurrencyType targetCurrencyType){
        Optional<Booking> optionalBooking = bookingOperationService.find(id, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent() || optionalBooking.get().getMoney().getCurrencyType().equals(targetCurrencyType)) return false;
        CurrencyExchangeRequest currencyExchangeRequest = CurrencyExchangeRequest.builder().sourceCurrencyType(optionalBooking.get().getMoney().getCurrencyType()).targetCurrencyType(targetCurrencyType).build();

        Optional<Double> rate = currencyExchangeOfficeService.getExchangeRate(currencyExchangeRequest);
        if (!rate.isPresent()) return false;

        Booking booking = optionalBooking.get();
        Money money = booking.getMoney();
        double amount = money.getAmount() * rate.get();
        amount = Math.round(amount*100.0)/100.0;
        booking.setMoney(new Money(amount, targetCurrencyType));
        bookingApplicationService.save(booking);
        return true;
    }
}
