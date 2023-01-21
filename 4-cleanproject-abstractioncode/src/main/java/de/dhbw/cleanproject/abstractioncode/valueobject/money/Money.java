package de.dhbw.cleanproject.abstractioncode.valueobject.money;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@RequiredArgsConstructor
public class Money {

    @Getter
    private double amount;
    @Getter
    private CurrencyType currencyType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Double.compare(money.amount, amount) == 0 && currencyType == money.currencyType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currencyType);
    }
}
