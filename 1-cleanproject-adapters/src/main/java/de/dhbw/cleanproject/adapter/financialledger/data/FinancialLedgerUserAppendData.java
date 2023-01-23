package de.dhbw.cleanproject.adapter.financialledger.data;

import de.dhbw.cleanproject.adapter.config.customvalidatior.ValueOfUUID;
import lombok.Data;

@Data
public class FinancialLedgerUserAppendData {

    @ValueOfUUID(message = "The userId is invalid UUID.")
    private String userId;

}