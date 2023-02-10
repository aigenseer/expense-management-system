package de.dhbw.ems.adapter.model.user.userdata;

import de.dhbw.ems.adapter.model.customvalidatior.ValueOfUUID;
import lombok.Data;

@Data
public class AppendUserData {

    @ValueOfUUID(message = "The userId is invalid UUID.")
    private String userId;

}