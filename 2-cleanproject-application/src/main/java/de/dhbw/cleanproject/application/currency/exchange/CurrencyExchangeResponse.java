package de.dhbw.cleanproject.application.currency.exchange;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CurrencyExchangeResponse {
    private LocalDate localDate;
    private Double rate;
}
