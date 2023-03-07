package de.dhbw.ems.abstractioncode.valueobject.money;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class MoneyTest {

    private final Double amount = 19.99;
    private final CurrencyType currencyType = CurrencyType.EURO;

    @Test
    public void create() {
       Money money = new Money(amount, currencyType);
       Assert.assertEquals(amount, money.getAmount());
       Assert.assertEquals(currencyType, money.getCurrencyType());
    }

    @Test
    public void roundingUp() {
        Money money = new Money(0.005, currencyType);
        Double expectedAmount = 0.01;
        Assert.assertEquals(expectedAmount, money.getAmount());
    }

    @Test
    public void roundingDown() {
        Money money = new Money(0.004, currencyType);
        Double expectedAmount = 0.00;
        Assert.assertEquals(expectedAmount, money.getAmount());
    }

    @Test
    public void testEquals() {
        Money money1 = new Money(amount, currencyType);
        Money money2 = new Money(amount, currencyType);
        Money money3 = new Money(99.99, currencyType);
        Money money4 = new Money(amount, CurrencyType.DOLLAR);
        Assert.assertEquals(money1, money2);
        Assert.assertNotEquals(money1, money3);
        Assert.assertNotEquals(money1, money4);
    }



}
