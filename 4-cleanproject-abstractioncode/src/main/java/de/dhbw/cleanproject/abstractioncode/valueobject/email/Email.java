package de.dhbw.cleanproject.abstractioncode.valueobject.email;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@RequiredArgsConstructor
public class Email {

    @Getter
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
