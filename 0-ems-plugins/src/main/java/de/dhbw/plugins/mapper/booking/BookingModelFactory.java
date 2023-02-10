package de.dhbw.plugins.mapper.booking;

import de.dhbw.ems.adapter.model.booking.model.BookingModel;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.plugins.rest.booking.BookingController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class BookingModelFactory {

    private final BookingToBookingModelMapper bookingToBookingModelMapper;

    public BookingModel create(UUID userId, UUID financialLedgerId, Booking booking){
        BookingModel model = bookingToBookingModelMapper.apply(BookingToBookingModelMapper
                .Context.builder()
                .userId(userId)
                .booking(booking)
                .build());
        model.removeLinks();
        Link selfLink = linkTo(methodOn(BookingController.class).findOne(userId, financialLedgerId, booking.getId())).withSelfRel()
                .andAffordance(afford(methodOn(BookingController.class).update(userId, financialLedgerId, booking.getId(), null)))
                .andAffordance(afford(methodOn(BookingController.class).delete(userId, financialLedgerId, booking.getId())));
        model.add(selfLink);
        return model;
    }
}
