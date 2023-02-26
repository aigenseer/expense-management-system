package de.dhbw.plugins.mapper.bookingcategory;

import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.plugins.rest.bookingcategories.BookingCategoriesController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class BookingCategoryPreviewCollectionModelFactory {

    private final BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper bookingCategoriesToBookingCategoryPreviewCollectionMapper;

    public BookingCategoryPreviewCollectionModel create(UUID userId, UUID financialLedgerAggregateId, Iterable<BookingCategoryAggregate> bookingCategoryAggregates){
        BookingCategoryPreviewCollectionModel previewCollectionModel = bookingCategoriesToBookingCategoryPreviewCollectionMapper
                .apply(BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper.Context.builder()
                        .userId(userId)
                        .bookingCategoryAggregates(bookingCategoryAggregates)
                        .build());
        Link selfLink = linkTo(methodOn(BookingCategoriesController.class).listAll(userId, financialLedgerAggregateId)).withSelfRel()
                .andAffordance(afford(methodOn(BookingCategoriesController.class).create(userId, financialLedgerAggregateId, null)));
        previewCollectionModel.add(selfLink);
        return previewCollectionModel;
    }
}
