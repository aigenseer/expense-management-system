package de.dhbw.ems.application.mediator.service.impl;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;

import java.util.UUID;

public interface ExchangeCurrencyServicePort {

    boolean exchangeCurrencyOfBooking(UUID id, UUID financialLedgerId, UUID bookingId, CurrencyType targetCurrencyType);

}
