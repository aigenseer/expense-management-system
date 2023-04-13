package de.dhbw.ems.application.mediator.service;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.abstractioncode.valueobject.money.Money;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeOfficeService;
import de.dhbw.ems.application.currency.exchange.CurrencyExchangeRequest;
import de.dhbw.ems.application.domain.service.booking.aggregate.BookingAggregateDomainService;
import de.dhbw.ems.application.mediator.service.impl.ExchangeCurrencyService;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExchangeCurrencyOperationService implements ExchangeCurrencyService {

    private final BookingAggregateDomainService bookingAggregateDomainService;
    private final BookingOperationService bookingOperationService;
    private final CurrencyExchangeOfficeService currencyExchangeOfficeService;

    @Transactional
    public boolean exchangeCurrencyOfBooking(UUID userId, UUID financialLedgerId, UUID bookingAggregateId, CurrencyType targetCurrencyType){
        Optional<BookingAggregate> optionalBooking = bookingOperationService.find(userId, financialLedgerId, bookingAggregateId);
        if (!optionalBooking.isPresent() || optionalBooking.get().getMoney().getCurrencyType().equals(targetCurrencyType)) return false;
        CurrencyExchangeRequest currencyExchangeRequest = CurrencyExchangeRequest.builder().sourceCurrencyType(optionalBooking.get().getMoney().getCurrencyType()).targetCurrencyType(targetCurrencyType).build();

        Optional<Double> rate = currencyExchangeOfficeService.getExchangeRate(currencyExchangeRequest);
        if (!rate.isPresent()) return false;

        BookingAggregate bookingAggregate = optionalBooking.get();
        Money money = bookingAggregate.getMoney();
        double amount = money.getAmount() * rate.get();
        bookingAggregate.setMoney(new Money(amount, targetCurrencyType));
        bookingAggregateDomainService.save(bookingAggregate);
        return true;
    }
}
