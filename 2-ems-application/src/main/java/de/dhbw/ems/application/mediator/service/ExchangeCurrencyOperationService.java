package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.abstractioncode.valueobject.money.Money;
import de.dhbw.ems.application.booking.aggregate.BookingAggregateDomainService;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeOfficeService;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeRequest;
import de.dhbw.ems.application.mediator.service.impl.ExchangeCurrencyService;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExchangeCurrencyOperationService implements ExchangeCurrencyService {

    private final BookingAggregateDomainService bookingAggregateDomainService;
    private final BookingOperationService bookingOperationService;
    private final CurrencyExchangeOfficeService currencyExchangeOfficeService;

    public boolean exchangeCurrencyOfBooking(UUID id, UUID financialLedgerId, UUID bookingId, CurrencyType targetCurrencyType){
        Optional<BookingAggregate> optionalBooking = bookingOperationService.find(id, financialLedgerId, bookingId);
        if (!optionalBooking.isPresent() || optionalBooking.get().getBooking().getMoney().getCurrencyType().equals(targetCurrencyType)) return false;
        CurrencyExchangeRequest currencyExchangeRequest = CurrencyExchangeRequest.builder().sourceCurrencyType(optionalBooking.get().getBooking().getMoney().getCurrencyType()).targetCurrencyType(targetCurrencyType).build();

        Optional<Double> rate = currencyExchangeOfficeService.getExchangeRate(currencyExchangeRequest);
        if (!rate.isPresent()) return false;

        BookingAggregate bookingAggregate = optionalBooking.get();
        Money money = bookingAggregate.getBooking().getMoney();
        double amount = money.getAmount() * rate.get();
        amount = Math.round(amount*100.0)/100.0;
        bookingAggregate.getBooking().setMoney(new Money(amount, targetCurrencyType));
        bookingAggregateDomainService.save(bookingAggregate);
        return true;
    }
}
