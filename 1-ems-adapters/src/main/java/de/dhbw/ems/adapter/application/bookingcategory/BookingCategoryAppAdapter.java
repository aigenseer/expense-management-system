package de.dhbw.ems.adapter.application.bookingcategory;

import de.dhbw.ems.application.bookingcategory.BookingCategoryAttributeData;
import de.dhbw.ems.application.mediator.service.impl.BookingCategoryServicePort;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingCategoryAppAdapter implements BookingCategoryApplicationAdapter {

    private final BookingCategoryServicePort bookingCategoryServicePort;

    @Override
    public Optional<BookingCategory> find(UUID id, UUID financialLedgerId, UUID bookingCategoryId) {
        return bookingCategoryServicePort.find(id, financialLedgerId, bookingCategoryId);
    }

    @Override
    public boolean exists(UUID id, UUID financialLedgerId, UUID bookingCategoryId) {
        return bookingCategoryServicePort.exists(id, financialLedgerId, bookingCategoryId);
    }

    @Override
    public boolean delete(UUID id, UUID financialLedgerId, UUID bookingCategoryId) {
        return bookingCategoryServicePort.delete(id, financialLedgerId, bookingCategoryId);
    }

    @Override
    public Optional<BookingCategory> create(UUID id, UUID financialLedgerId, BookingCategoryAttributeData attributeData) {
        return bookingCategoryServicePort.create(id, financialLedgerId, attributeData);
    }

}
