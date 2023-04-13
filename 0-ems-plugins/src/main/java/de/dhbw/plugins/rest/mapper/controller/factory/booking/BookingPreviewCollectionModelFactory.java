package de.dhbw.plugins.rest.mapper.controller.factory.booking;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.plugins.rest.controller.bookings.BookingsController;
import de.dhbw.plugins.rest.mapper.controller.model.booking.BookingsToBookingPreviewCollectionMapper;
import de.dhbw.plugins.rest.mapper.model.booking.preview.BookingPreviewCollectionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class BookingPreviewCollectionModelFactory {

    private final BookingsToBookingPreviewCollectionMapper bookingsToBookingPreviewCollectionMapper;

    public BookingPreviewCollectionModel create(UUID userId, UUID financialLedgerId, Iterable<BookingAggregate> bookingAggregates){
        BookingPreviewCollectionModel previewCollectionModel = bookingsToBookingPreviewCollectionMapper.apply(BookingsToBookingPreviewCollectionMapper
                .Context.builder()
                .userId(userId)
                .bookingAggregates(bookingAggregates)
                .build());

        Link selfLink = linkTo(methodOn(BookingsController.class).listAll(userId, financialLedgerId)).withSelfRel()
                .andAffordance(afford(methodOn(BookingsController.class).create(userId, financialLedgerId, null)));
        previewCollectionModel.add(selfLink);
        return previewCollectionModel;
    }
}
