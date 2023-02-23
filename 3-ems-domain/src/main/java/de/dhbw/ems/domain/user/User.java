package de.dhbw.ems.domain.user;

import de.dhbw.ems.abstractioncode.valueobject.email.Email;
import de.dhbw.ems.abstractioncode.valueobject.phonennumber.PhoneNumber;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "ems_user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    @Column(name = "email", nullable = false)
    private Email email;

    @Embedded
    @Column(name = "phone_number", nullable = true)
    private PhoneNumber phoneNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="ems_user_to_financial_ledger",
            joinColumns=@JoinColumn(name="ems_user_id", referencedColumnName = "id"),
            inverseJoinColumns=@JoinColumn(name="financial_ledger_id", referencedColumnName = "id")
    )
    private Set<FinancialLedger> financialLedgers;
    
}
