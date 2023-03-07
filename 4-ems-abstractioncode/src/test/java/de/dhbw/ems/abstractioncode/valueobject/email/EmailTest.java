package de.dhbw.ems.abstractioncode.valueobject.email;

import org.junit.Assert;
import org.junit.Test;

public class EmailTest {

    private final String mailAddress1 =  "example@mail.de";
    private final String mailAddress2 =  "example@mail.de";
    private final String mailAddress3 =  "example.mail.de";

    @Test
    public void create() {
       Email email = new Email(mailAddress1);
       Assert.assertEquals(mailAddress1, email.getMailAddress());
    }

    @Test
    public void testEquals() {
        Email email1 = new Email(mailAddress1);
        Email email2 = new Email(mailAddress2);
        Assert.assertEquals(email1, email1);
        Assert.assertEquals(email1, email2);
    }

    @Test(expected = RuntimeException.class)
    public void testException(){
        new Email(mailAddress3);
    }

}
