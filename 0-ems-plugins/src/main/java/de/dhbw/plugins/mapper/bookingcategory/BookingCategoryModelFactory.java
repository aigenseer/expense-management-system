package de.dhbw.plugins.mapper.bookingcategory;

import de.dhbw.ems.adapter.model.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.ems.adapter.model.bookingcategory.data.BookingCategoryAggregateToBookingCategoryModelAdapterMapper;
import de.dhbw.ems.adapter.model.bookingcategory.data.BookingCategoryModel;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.plugins.mapper.booking.BookingPreviewCollectionModelFactory;
import de.dhbw.plugins.rest.controller.bookingcategory.BookingCategoryController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class BookingCategoryModelFactory {

    private final BookingCategoryAggregateToBookingCategoryModelAdapterMapper bookingCategoryAggregateToBookingCategoryModelAdapterMapper;
    private final BookingPreviewCollectionModelFactory bookingPreviewCollectionModelFactory;

    public BookingCategoryModel create(UUID userId, UUID financialLedgerAggregateId, BookingCategoryAggregate bookingCategoryAggregate){
        BookingPreviewCollectionModel previewCollectionModel = bookingPreviewCollectionModelFactory.create(userId, financialLedgerAggregateId, bookingCategoryAggregate.getBookingAggregates());

        BookingCategoryModel model = bookingCategoryAggregateToBookingCategoryModelAdapterMapper.apply(bookingCategoryAggregate);
        model.setBookingPreviewCollectionModel(previewCollectionModel);

        Link selfLink = linkTo(methodOn(BookingCategoryController.class).findOne(userId, financialLedgerAggregateId, bookingCategoryAggregate.getId())).withSelfRel()
                .andAffordance(afford(methodOn(BookingCategoryController.class).update(userId, financialLedgerAggregateId, bookingCategoryAggregate.getId(), null)))
                .andAffordance(afford(methodOn(BookingCategoryController.class).delete(userId, financialLedgerAggregateId, bookingCategoryAggregate.getId())));
        model.add(selfLink);
        return model;
    }
}
