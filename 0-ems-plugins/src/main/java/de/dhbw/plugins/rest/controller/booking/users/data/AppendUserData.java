package de.dhbw.plugins.rest.controller.booking.users.data;

import de.dhbw.plugins.rest.customvalidatior.ValueOfUUID;
import lombok.Data;

@Data
public class AppendUserData {

    @ValueOfUUID(message = "The userId is invalid UUID.")
    private String userId;

}