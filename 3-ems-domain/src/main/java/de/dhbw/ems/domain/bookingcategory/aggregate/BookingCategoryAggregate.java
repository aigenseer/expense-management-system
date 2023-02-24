package de.dhbw.ems.domain.bookingcategory.aggregate;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "booking_category_aggregate")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingCategoryAggregate {

    @Id
    @Column(name = "id", nullable = false)
    @Type(type="uuid-char")
    private UUID id;

    @Column(name="booking_category_id", nullable=false)
    @Type(type="uuid-char")
    private UUID bookingCategoryId;

    @Column(name="financial_ledger_id", nullable=false)
    @Type(type="uuid-char")
    private UUID financialLedgerId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Booking_category_id", nullable = false, updatable = false, insertable = false)
    private BookingCategory bookingCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_ledger_id", nullable = false, updatable = false, insertable = false)
    private FinancialLedger financialLedger;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="categoryAggregate", targetEntity = BookingAggregate.class)
    private Set<BookingAggregate> bookingAggregates;

}
