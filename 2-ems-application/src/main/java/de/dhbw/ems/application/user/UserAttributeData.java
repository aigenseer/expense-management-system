package de.dhbw.ems.application.user;

import de.dhbw.cleanproject.abstractioncode.valueobject.email.Email;
import de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber.PhoneNumber;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAttributeData {
    private String name;
    private Email email;
    private PhoneNumber phoneNumber;
}
