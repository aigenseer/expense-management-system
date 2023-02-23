package de.dhbw.ems.domain.bookingcategory.aggregate;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "booking_category")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingCategoryAggregate {

    @EmbeddedId
    private BookingCategoryId id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Booking_category_id", nullable = false, updatable = false, insertable = false)
    private BookingCategory bookingCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_ledger_id", nullable = false, updatable = false, insertable = false)
    private FinancialLedger financialLedger;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="category", targetEntity = BookingAggregate.class)
    private Set<BookingAggregate> bookingAggregates;

}
