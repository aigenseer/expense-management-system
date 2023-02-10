package de.dhbw.cleanproject.application.mediator.service.impl;

import de.dhbw.cleanproject.application.bookingcategory.BookingCategoryAttributeData;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;

import java.util.Optional;
import java.util.UUID;

public interface BookingCategoryService {

    Optional<BookingCategory> find(UUID id, UUID financialLedgerId, UUID bookingCategoryId);

    boolean exists(UUID id, UUID financialLedgerId, UUID bookingCategoryId);

    boolean delete(UUID id, UUID financialLedgerId, UUID bookingCategoryId);

    Optional<BookingCategory> create(UUID id, UUID financialLedgerId, BookingCategoryAttributeData attributeData);

}
