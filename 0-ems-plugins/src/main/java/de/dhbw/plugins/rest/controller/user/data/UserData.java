package de.dhbw.plugins.rest.controller.user.data;

import de.dhbw.ems.abstractioncode.valueobject.phonennumber.InternationalPhoneCode;
import de.dhbw.ems.adapter.mapper.data.user.IUserUnsafeData;
import de.dhbw.plugins.rest.customvalidatior.ValueOfEnum;
import lombok.Data;

import javax.validation.constraints.*;


@Data
public class UserData implements IUserUnsafeData {

    @NotEmpty(message = "The full name is required.")
    @Size(min = 2, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    private String name;

    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;

    @Min(1)
    private Integer phoneNumber;

    @ValueOfEnum(enumClass = InternationalPhoneCode.class, message = "Invalid international phone code")
    private String internationalPhoneCode;

}