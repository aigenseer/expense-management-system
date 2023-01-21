package de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber;


import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@RequiredArgsConstructor
public class PhoneNumber {

    @Getter
    private double phoneNumber;
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
}
