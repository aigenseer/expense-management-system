package de.dhbw.ems.abstractioncode.valueobject.money;


import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Money {

    private Double amount;
    private CurrencyType currencyType;

    public Money(
            final Double amount,
            final CurrencyType currencyType
    ){
        validateAmount(amount);
        this.amount = formatAmount(amount);
        this.currencyType = currencyType;
    }

    protected Money() {}

    private void validateAmount(final Double amount){
        if (amount < 0){
            throw new RuntimeException("Quantity cannot be negative.");
        }
    }

    private Double formatAmount(Double amount){
        return Math.round(amount*100.0)/100.0;
    }

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
