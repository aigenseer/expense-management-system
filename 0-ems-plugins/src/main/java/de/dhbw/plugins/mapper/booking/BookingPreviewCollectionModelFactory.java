package de.dhbw.plugins.mapper.booking;

import de.dhbw.ems.adapter.model.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.plugins.rest.bookings.BookingsController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class BookingPreviewCollectionModelFactory {

    private final BookingsToBookingPreviewCollectionMapper bookingsToBookingPreviewCollectionMapper;

    public BookingPreviewCollectionModel create(UUID userId, UUID financialLedgerId, Iterable<Booking> bookings){
        BookingPreviewCollectionModel previewCollectionModel = bookingsToBookingPreviewCollectionMapper.apply(BookingsToBookingPreviewCollectionMapper
                .Context.builder()
                .userId(userId)
                .bookings(bookings)
                .build());

        Link selfLink = linkTo(methodOn(BookingsController.class).listAll(userId, financialLedgerId)).withSelfRel()
                .andAffordance(afford(methodOn(BookingsController.class).create(userId, financialLedgerId, null)));
        previewCollectionModel.add(selfLink);
        return previewCollectionModel;
    }
}
