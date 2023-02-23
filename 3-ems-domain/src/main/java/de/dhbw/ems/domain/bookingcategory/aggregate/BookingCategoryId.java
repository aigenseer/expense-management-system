package de.dhbw.ems.domain.bookingcategory.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BookingCategoryId implements Serializable {

    @Column(name="booking_category_id", nullable=false)
    @Type(type="uuid-char")
    UUID bookingCategoryId;

    @Column(name="financialLedger_id", nullable=false)
    @Type(type="uuid-char")
    UUID financialLedgerId;

}
