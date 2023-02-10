package de.dhbw.ems.adapter.model.user.updatedata;

import de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber.InternationalPhoneCode;
import de.dhbw.ems.adapter.model.customvalidatior.ValueOfEnum;
import de.dhbw.ems.adapter.model.user.userdata.IUserUnsafeData;
import lombok.Data;

import javax.validation.constraints.*;


@Data
public class UserUpdateData implements IUserUnsafeData {

    @Size(min = 2, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    private String name;

    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;

    @Min(1)
    private Integer phoneNumber;

    @ValueOfEnum(enumClass = InternationalPhoneCode.class, message = "Invalid international phone code")
    private String internationalPhoneCode;

}