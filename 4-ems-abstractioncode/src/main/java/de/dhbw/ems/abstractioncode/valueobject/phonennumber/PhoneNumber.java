package de.dhbw.ems.abstractioncode.valueobject.phonennumber;


import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class PhoneNumber  {

    private Integer number;
    private InternationalPhoneCode internationalPhoneCode;

    public PhoneNumber(){}

    public PhoneNumber(
            final Integer number,
            final InternationalPhoneCode internationalPhoneCode
    ){
        validatePhoneNumber(number);
        this.number = number;
        this.internationalPhoneCode = internationalPhoneCode;
    }

    private void validatePhoneNumber(final Integer number){
        if (number <= 1){
            throw new RuntimeException("Number cannot be negative.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Double.compare(that.number, number) == 0 && internationalPhoneCode == that.internationalPhoneCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, internationalPhoneCode);
    }

    @Override
    public String toString(){
        return String.format("%s %s", internationalPhoneCode.getCode(), number);
    }

}
