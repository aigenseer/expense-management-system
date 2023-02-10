package de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@RequiredArgsConstructor
@AllArgsConstructor
public class PhoneNumber  {

    @Getter
    private Integer phoneNumber;
    @Getter
    private InternationalPhoneCode internationalPhoneCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Double.compare(that.phoneNumber, phoneNumber) == 0 && internationalPhoneCode == that.internationalPhoneCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, internationalPhoneCode);
    }

    @Override
    public String toString(){
        return String.format("%s %s", internationalPhoneCode.getCode(), phoneNumber);
    }

}
