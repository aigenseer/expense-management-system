package de.dhbw.cleanproject.adapter.model.user.userdata;

import de.dhbw.cleanproject.adapter.model.customvalidatior.ValueOfUUID;
import lombok.Data;

@Data
public class AppendUserData {

    @ValueOfUUID(message = "The userId is invalid UUID.")
    private String userId;

}