package de.dhbw.ems.abstractioncode.valueobject.phonennumber;

import org.junit.Assert;
import org.junit.Test;

public class PhoneNumberTest {

    private final Integer number = 1234567;
    private final InternationalPhoneCode internationalPhoneCode = InternationalPhoneCode.DE;

    @Test
    public void create() {
        PhoneNumber phoneNumber = new PhoneNumber(number, internationalPhoneCode);
        Assert.assertEquals(number, phoneNumber.getNumber());
        Assert.assertEquals(internationalPhoneCode, phoneNumber.getInternationalPhoneCode());
    }

    @Test
    public void testEquals() {
        PhoneNumber phoneNumber1 = new PhoneNumber(number, internationalPhoneCode);
        PhoneNumber phoneNumber2 = new PhoneNumber(number, internationalPhoneCode);
        PhoneNumber phoneNumber3 = new PhoneNumber(number, InternationalPhoneCode.US);
        PhoneNumber phoneNumber4 = new PhoneNumber(7654321, InternationalPhoneCode.US);
        Assert.assertEquals(phoneNumber1, phoneNumber2);
        Assert.assertNotEquals(phoneNumber1, phoneNumber3);
        Assert.assertNotEquals(phoneNumber1, phoneNumber4);
    }

    @Test(expected = RuntimeException.class)
    public void testException() {
        new PhoneNumber(-1, internationalPhoneCode);
    }

}
