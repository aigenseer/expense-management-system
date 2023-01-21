package de.dhbw.cleanproject.domain.bookingcategory;

import de.dhbw.cleanproject.domain.booking.Booking;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "booking_category")
@Getter
public class BookingCategory {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Booking> bookings;

}
