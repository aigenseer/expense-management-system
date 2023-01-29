package de.dhbw.cleanproject.domain.booking;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.Money;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.financialledger.FinancialLedger;
import de.dhbw.cleanproject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "booking")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Booking {

    @Id
    @Column(name = "id", nullable = false)
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Embedded
    @Column(name = "title", nullable = false)
    private Money money;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "financial_ledger_id", nullable = false, updatable = false, insertable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FinancialLedger financialLedger;

    @Column(name="financial_ledger_id", nullable=false)
    @Type(type="uuid-char")
    private UUID financialLedgerId;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "referenced_users")
    private Set<User> referencedUsers;

    @ManyToOne
    @JoinColumn(name = "referenced_category_id", nullable = true)
    private BookingCategory category;


}
