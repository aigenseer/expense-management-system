package de.dhbw.cleanproject.application.financialledger;

import de.dhbw.cleanproject.abstractioncode.valueobject.email.Email;
import de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber.PhoneNumber;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinancialLedgerAttributeData {
    private String name;
    private Email email;
    private PhoneNumber phoneNumber;
}
