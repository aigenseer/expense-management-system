package de.dhbw.plugins.mapper.booking;

import de.dhbw.ems.adapter.model.booking.model.BookingModel;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.plugins.rest.controller.booking.BookingController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class BookingModelFactory {

    private final BookingToBookingModelMapper bookingToBookingModelMapper;

    public BookingModel create(UUID userId, UUID financialLedgerId, BookingAggregate bookingAggregate){
        BookingModel model = bookingToBookingModelMapper.apply(BookingToBookingModelMapper
                .Context.builder()
                .userId(userId)
                .bookingAggregate(bookingAggregate)
                .build());
        model.removeLinks();
        Link selfLink = linkTo(methodOn(BookingController.class).findOne(userId, financialLedgerId, bookingAggregate.getId())).withSelfRel()
                .andAffordance(afford(methodOn(BookingController.class).update(userId, financialLedgerId, bookingAggregate.getId(), null)))
                .andAffordance(afford(methodOn(BookingController.class).delete(userId, financialLedgerId, bookingAggregate.getId())));
        model.add(selfLink);
        return model;
    }
}
