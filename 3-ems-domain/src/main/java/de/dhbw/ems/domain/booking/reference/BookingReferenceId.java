package de.dhbw.ems.domain.booking.reference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BookingReferenceId implements Serializable {

    @Column(name="booking_aggregate_id", nullable=false)
    @Type(type="uuid-char")
    UUID bookingAggregateId;

    @Column(name="user_id", nullable=false)
    @Type(type="uuid-char")
    UUID userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingReferenceId that = (BookingReferenceId) o;
        return Objects.equals(bookingAggregateId, that.bookingAggregateId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingAggregateId, userId);
    }
}
