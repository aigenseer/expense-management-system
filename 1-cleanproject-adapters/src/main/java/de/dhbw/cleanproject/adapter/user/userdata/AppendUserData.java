package de.dhbw.cleanproject.adapter.user.userdata;

import de.dhbw.cleanproject.adapter.config.customvalidatior.ValueOfUUID;
import lombok.Data;

@Data
public class AppendUserData {

    @ValueOfUUID(message = "The userId is invalid UUID.")
    private String userId;

}