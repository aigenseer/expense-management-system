package de.dhbw.ems.application.mediator.service.impl;

import de.dhbw.ems.application.bookingcategory.BookingCategoryAttributeData;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryServicePort {

    Optional<BookingCategory> find(UUID id, UUID financialLedgerId, UUID bookingCategoryId);

    boolean exists(UUID id, UUID financialLedgerId, UUID bookingCategoryId);

    boolean delete(UUID id, UUID financialLedgerId, UUID bookingCategoryId);

    Optional<BookingCategory> create(UUID id, UUID financialLedgerId, BookingCategoryAttributeData attributeData);

}
