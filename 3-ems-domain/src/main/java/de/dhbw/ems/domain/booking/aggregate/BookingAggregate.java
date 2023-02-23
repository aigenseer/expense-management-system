package de.dhbw.ems.domain.booking.aggregate;

import de.dhbw.ems.domain.booking.entity.Booking;
import de.dhbw.ems.domain.booking.reference.BookingReference;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import de.dhbw.ems.domain.user.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "booking_aggregate")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingAggregate implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @Type(type="uuid-char")
    private UUID id;

    @Column(name="booking_id", nullable=false)
    @Type(type="uuid-char")
    private UUID bookingId;

    @Column(name="financial_ledger_id", nullable=false)
    @Type(type="uuid-char")
    private UUID financialLedgerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false, updatable = false, insertable = false)
    private User creator;

    @Column(name="creator_id", nullable=false)
    @Type(type="uuid-char")
    private UUID creatorId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false, updatable = false, insertable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "financial_ledger_id", nullable = false, updatable = false, insertable = false)
    private FinancialLedger financialLedger;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referenced_category_id", nullable = true)
    private BookingCategory category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookingAggregate", targetEntity = BookingReference.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<BookingReference> bookingReferences;

    public Set<User> getReferencedUsers(){
        return bookingReferences.stream().map(BookingReference::getUser).collect(Collectors.toSet());
    }

}
