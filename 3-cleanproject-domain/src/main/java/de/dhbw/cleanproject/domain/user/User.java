package de.dhbw.cleanproject.domain.user;

import de.dhbw.cleanproject.abstractioncode.valueobject.email.Email;
import de.dhbw.cleanproject.abstractioncode.valueobject.phonennumber.PhoneNumber;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "ems_user")
@Getter
public class User {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String Name;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Embedded
    @Column(name = "email", nullable = false)
    private Email email;

    @Embedded
    @Column(name = "phone_number", nullable = true)
    private PhoneNumber phoneNumber = null;

    @ManyToMany(fetch = FetchType.LAZY)
    @Column(name = "authorized_financial_ledgers")
    private Set<FinancialLedger> financialLedgers;



}
