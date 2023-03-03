package de.dhbw.ems.application.domain.service.user;

import de.dhbw.ems.abstractioncode.valueobject.email.Email;
import de.dhbw.ems.abstractioncode.valueobject.phonennumber.PhoneNumber;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAttributeData {
    private String name;
    private Email email;
    private PhoneNumber phoneNumber;
}
