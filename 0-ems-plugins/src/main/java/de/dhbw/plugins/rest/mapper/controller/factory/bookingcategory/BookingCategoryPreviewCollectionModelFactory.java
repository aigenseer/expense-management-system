package de.dhbw.plugins.rest.mapper.controller.factory.bookingcategory;

import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.plugins.rest.controller.bookingcategories.BookingCategoriesController;
import de.dhbw.plugins.rest.mapper.controller.model.bookingcategory.BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper;
import de.dhbw.plugins.rest.mapper.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class BookingCategoryPreviewCollectionModelFactory {

    private final BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper bookingCategoriesToBookingCategoryPreviewCollectionMapper;

    public BookingCategoryPreviewCollectionModel create(UUID userId, UUID financialLedgerId, Iterable<BookingCategoryAggregate> bookingCategoryAggregates){
        BookingCategoryPreviewCollectionModel previewCollectionModel = bookingCategoriesToBookingCategoryPreviewCollectionMapper
                .apply(BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper.Context.builder()
                        .userId(userId)
                        .bookingCategoryAggregates(bookingCategoryAggregates)
                        .build());
        Link selfLink = linkTo(methodOn(BookingCategoriesController.class).listAll(userId, financialLedgerId)).withSelfRel()
                .andAffordance(afford(methodOn(BookingCategoriesController.class).create(userId, financialLedgerId, null)));
        previewCollectionModel.add(selfLink);
        return previewCollectionModel;
    }
}
