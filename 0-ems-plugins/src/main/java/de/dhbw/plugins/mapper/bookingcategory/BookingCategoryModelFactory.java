package de.dhbw.plugins.mapper.bookingcategory;

import de.dhbw.ems.adapter.model.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.ems.adapter.model.bookingcategory.data.BookingCategoryModel;
import de.dhbw.ems.adapter.model.bookingcategory.data.BookingCategoryToBookingCategoryModelAdapterMapper;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.plugins.mapper.booking.BookingPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.bookingcategory.BookingCategoryController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class BookingCategoryModelFactory {

    private final BookingCategoryToBookingCategoryModelAdapterMapper bookingCategoryToBookingCategoryModelAdapterMapper;
    private final BookingPreviewCollectionModelFactory bookingPreviewCollectionModelFactory;

    public BookingCategoryModel create(UUID userId, UUID financialLedgerId, BookingCategory bookingCategory){
        BookingPreviewCollectionModel previewCollectionModel = bookingPreviewCollectionModelFactory.create(userId, financialLedgerId, bookingCategory.getBookings());

        BookingCategoryModel model = bookingCategoryToBookingCategoryModelAdapterMapper.apply(bookingCategory);
        model.setBookingPreviewCollectionModel(previewCollectionModel);

        Link selfLink = linkTo(methodOn(BookingCategoryController.class).findOne(userId, financialLedgerId, bookingCategory.getId())).withSelfRel()
                .andAffordance(afford(methodOn(BookingCategoryController.class).update(userId, financialLedgerId, bookingCategory.getId(), null)))
                .andAffordance(afford(methodOn(BookingCategoryController.class).delete(userId, financialLedgerId, bookingCategory.getId())));
        model.add(selfLink);
        return model;
    }
}
