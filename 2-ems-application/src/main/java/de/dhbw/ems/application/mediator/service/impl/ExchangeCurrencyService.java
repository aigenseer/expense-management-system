package de.dhbw.ems.application.mediator.service.impl;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;

import java.util.UUID;

public interface ExchangeCurrencyService {

    boolean exchangeCurrencyOfBooking(UUID id, UUID financialLedgerId, UUID bookingId, CurrencyType targetCurrencyType);

}
