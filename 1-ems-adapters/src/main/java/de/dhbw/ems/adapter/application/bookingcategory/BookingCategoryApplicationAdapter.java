package de.dhbw.ems.adapter.application.bookingcategory;

import de.dhbw.ems.application.bookingcategory.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryApplicationAdapter {

    Optional<BookingCategory> find(UUID id, UUID financialLedgerId, UUID bookingCategoryId);

    boolean exists(UUID id, UUID financialLedgerId, UUID bookingCategoryId);

    boolean delete(UUID id, UUID financialLedgerId, UUID bookingCategoryId);

    Optional<BookingCategory> create(UUID id, UUID financialLedgerId, BookingCategoryAttributeData attributeData);

    Optional<BookingCategory> updateByAttributeData(BookingCategory bookingCategory, BookingCategoryAttributeData data);

}
