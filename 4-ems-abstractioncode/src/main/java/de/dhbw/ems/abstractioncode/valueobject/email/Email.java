package de.dhbw.ems.abstractioncode.valueobject.email;


import lombok.Getter;
import org.apache.commons.validator.routines.EmailValidator;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Email {

    @Getter
    private String mailAddress;

    protected Email(){}

    public Email(final String mailAddress){
        validateMailAddress(mailAddress);
        this.mailAddress = mailAddress;
    }

    private void validateMailAddress(final String mailAddress){
        if (!EmailValidator.getInstance().isValid(mailAddress)){
            throw new RuntimeException("No valid email address");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(mailAddress, email1.mailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailAddress);
    }

    @Override
    public String toString(){
        return mailAddress;
    }
}
