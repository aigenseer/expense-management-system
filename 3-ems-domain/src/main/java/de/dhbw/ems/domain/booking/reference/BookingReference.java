package de.dhbw.ems.domain.booking.reference;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "booking_reference")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingReference {

    @EmbeddedId
    private BookingReferenceId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_aggregate_id", nullable = false, updatable = false, insertable = false)
    private BookingAggregate bookingAggregate;

}
