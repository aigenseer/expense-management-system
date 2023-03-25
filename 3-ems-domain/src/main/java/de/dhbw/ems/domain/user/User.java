package de.dhbw.ems.domain.user;

import de.dhbw.ems.abstractioncode.valueobject.email.Email;
import de.dhbw.ems.abstractioncode.valueobject.phonennumber.PhoneNumber;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.booking.reference.BookingReference;
import de.dhbw.ems.domain.financialledger.link.UserFinancialLedgerLink;
import lombok.*;
import org.apache.commons.lang3.Validate;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = BookingReference.class, cascade = CascadeType.REMOVE)
    private Set<BookingReference> bookingReferences;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator", targetEntity = BookingAggregate.class, cascade = CascadeType.REMOVE)
    private Set<BookingAggregate> createdBookings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = UserFinancialLedgerLink.class, cascade = CascadeType.REMOVE)
    private Set<UserFinancialLedgerLink> userFinancialLedgerLinks;

    public static UserBuilder builder() {
        return new CustomBuilder();
    }

    private static class CustomBuilder extends UserBuilder {
        public User build() {
            User object = super.build();
            Validate.notNull(object.getId());
            Validate.notBlank(object.getName());
            Validate.notNull(object.getId());
            Validate.notNull(object.getEmail());
            return object;
        }
    }
    
}
