package de.dhbw.plugins.persistence.hibernate.bookingcategory;

import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataBookingCategoryRepository extends JpaRepository<BookingCategory, UUID> {

}
