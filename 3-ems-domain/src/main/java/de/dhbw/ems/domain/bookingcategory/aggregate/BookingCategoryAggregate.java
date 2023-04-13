package de.dhbw.ems.domain.bookingcategory.aggregate;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import lombok.*;
import org.apache.commons.lang3.Validate;
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

    @Column(name="financial_ledger_id", nullable=false)
    @Type(type="uuid-char")
    private UUID financialLedgerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_ledger_id", nullable = false, updatable = false, insertable = false)
    private FinancialLedger financialLedger;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="categoryAggregate", targetEntity = BookingAggregate.class)
    private Set<BookingAggregate> bookingAggregates;

    public static BookingCategoryAggregate.BookingCategoryAggregateBuilder builder() {
        return new CustomBuilder();
    }

    private static class CustomBuilder extends BookingCategoryAggregate.BookingCategoryAggregateBuilder {
        public BookingCategoryAggregate build() {
            BookingCategoryAggregate object = super.build();
            Validate.notNull(object.getId());
            Validate.notBlank(object.getTitle());
            Validate.notNull(object.getFinancialLedgerId());
            return object;
        }
    }

}
