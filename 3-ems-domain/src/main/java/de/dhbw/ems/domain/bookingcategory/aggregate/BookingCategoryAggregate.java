package de.dhbw.ems.domain.bookingcategory.aggregate;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.financialledger.aggregate.FinancialLedgerAggregate;
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

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name="financial_ledger_aggregate_id", nullable=false)
    @Type(type="uuid-char")
    private UUID financialLedgerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_ledger_aggregate_id", nullable = false, updatable = false, insertable = false)
    private FinancialLedgerAggregate financialLedgerAggregate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="categoryAggregate", targetEntity = BookingAggregate.class)
    private Set<BookingAggregate> bookingAggregates;

}
