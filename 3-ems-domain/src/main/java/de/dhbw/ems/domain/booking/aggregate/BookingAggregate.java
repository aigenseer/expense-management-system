package de.dhbw.ems.domain.booking.aggregate;

import de.dhbw.ems.abstractioncode.valueobject.money.Money;
import de.dhbw.ems.domain.booking.reference.BookingReference;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import de.dhbw.ems.domain.user.User;
import lombok.*;
import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
public class BookingAggregate {

    @Id
    @Column(name = "id", nullable = false)
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Embedded
    @Column(name = "title", nullable = false)
    private Money money;

    @Column(name="financial_ledger_id", nullable=false)
    @Type(type="uuid-char")
    private UUID financialLedgerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false, updatable = false, insertable = false)
    private User creator;

    @Column(name="creator_id", nullable=false)
    @Type(type="uuid-char")
    private UUID creatorId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "financial_ledger_id", nullable = false, updatable = false, insertable = false)
    private FinancialLedger financialLedger;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name="aggregate_category_id", nullable=true)
    @Type(type="uuid-char")
    private UUID categoryAggregateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aggregate_category_id", nullable = true, updatable = false, insertable = false)
    private BookingCategoryAggregate categoryAggregate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookingAggregate", targetEntity = BookingReference.class, cascade = CascadeType.REMOVE)
    private Set<BookingReference> bookingReferences;

    public Set<User> getReferencedUsers(){
        return bookingReferences.stream().map(BookingReference::getUser).collect(Collectors.toSet());
    }

    public static BookingAggregate.BookingAggregateBuilder builder() {
        return new CustomBuilder();
    }

    private static class CustomBuilder extends BookingAggregate.BookingAggregateBuilder {
        public BookingAggregate build() {
            BookingAggregate object = super.build();
            Validate.notNull(object.getId());
            Validate.notBlank(object.getTitle());
            Validate.notNull(object.getMoney());
            Validate.notNull(object.getCreatorId());
            Validate.notNull(object.getCreationDate());
            Validate.notNull(object.getFinancialLedgerId());
            return object;
        }
    }

}
