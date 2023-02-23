package de.dhbw.plugins.mapper.bookingcategory;

import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
import de.dhbw.plugins.rest.bookingcategories.BookingCategoriesController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class BookingCategoryPreviewCollectionModelFactory {

    private final BookingCategoriesToBookingCategoryPreviewCollectionMapper bookingCategoriesToBookingCategoryPreviewCollectionMapper;

    public BookingCategoryPreviewCollectionModel create(UUID userId, UUID financialLedgerId, Iterable<BookingCategory> bookingCategories){
        BookingCategoryPreviewCollectionModel previewCollectionModel = bookingCategoriesToBookingCategoryPreviewCollectionMapper
                .apply(BookingCategoriesToBookingCategoryPreviewCollectionMapper.Context.builder()
                        .userId(userId)
                        .bookingCategories(bookingCategories)
                        .build());
        Link selfLink = linkTo(methodOn(BookingCategoriesController.class).listAll(userId, financialLedgerId)).withSelfRel()
                .andAffordance(afford(methodOn(BookingCategoriesController.class).create(userId, financialLedgerId, null)));
        previewCollectionModel.add(selfLink);
        return previewCollectionModel;
    }
}
