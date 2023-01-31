package de.dhbw.cleanproject.domain.financialledger;

import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "financial_ledger")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FinancialLedger {

    @Id
    @Column(name = "id", nullable = false)
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="ems_user_to_financial_ledger",
            joinColumns=@JoinColumn(name="financial_ledger_id", referencedColumnName = "id"),
            inverseJoinColumns=@JoinColumn(name="ems_user_id", referencedColumnName = "id")
    )
    private Set<User> authorizedUser;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Booking> bookings;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<BookingCategory> bookingCategories;


}
