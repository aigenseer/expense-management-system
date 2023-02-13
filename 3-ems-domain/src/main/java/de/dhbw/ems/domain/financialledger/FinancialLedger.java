package de.dhbw.ems.domain.financialledger;

import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.ems.domain.user.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
@Setter
public class FinancialLedger {

    @Id
    @Column(name = "id", nullable = false)
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="ems_user_to_financial_ledger",
            joinColumns=@JoinColumn(name="financial_ledger_id", referencedColumnName = "id"),
            inverseJoinColumns=@JoinColumn(name="ems_user_id", referencedColumnName = "id")
    )
    private Set<User> authorizedUser;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy="financialLedger", targetEntity = Booking.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Booking> bookings;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy="financialLedger", targetEntity = BookingCategory.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<BookingCategory> bookingCategories;


}
