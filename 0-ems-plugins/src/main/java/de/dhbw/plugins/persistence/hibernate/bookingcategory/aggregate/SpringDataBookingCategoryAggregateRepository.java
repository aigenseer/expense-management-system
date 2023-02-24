package de.dhbw.plugins.persistence.hibernate.bookingcategory.aggregate;

import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataBookingCategoryAggregateRepository extends JpaRepository<BookingCategoryAggregate, UUID> {

}
