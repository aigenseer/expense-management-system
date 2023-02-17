package de.dhbw.ems.domain.bookingcategory;

import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.financialledger.FinancialLedger;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "booking_category")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingCategory {

    @Id
    @Column(name = "id", nullable = false)
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="category")
    private Set<Booking> bookings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_ledger_id")
    private FinancialLedger financialLedger;

}
