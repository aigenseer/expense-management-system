package de.dhbw.cleanproject.domain.financialledger;

import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "financial_ledger")
public class FinancialLedger {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToMany(fetch = FetchType.LAZY)
    @Column(name = "authorized_user")
    @Getter
    private Set<User> authorizedUser;

    @OneToMany(fetch = FetchType.LAZY)
    @Getter
    private Set<Booking> bookings;

    @OneToMany(fetch = FetchType.LAZY)
    @Getter
    private Set<BookingCategory> bookingCategories;


}
