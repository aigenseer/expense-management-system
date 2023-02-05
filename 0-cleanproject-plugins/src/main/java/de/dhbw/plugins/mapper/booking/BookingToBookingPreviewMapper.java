package de.dhbw.plugins.mapper.booking;

import de.dhbw.cleanproject.adapter.model.booking.preview.BookingPreviewModel;
import de.dhbw.cleanproject.adapter.model.booking.preview.BookingToBookingPreviewModelAdapterMapper;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.plugins.rest.booking.BookingController;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
@RequiredArgsConstructor
public class BookingToBookingPreviewMapper implements Function<BookingToBookingPreviewMapper.Context, BookingPreviewModel> {

    @RequiredArgsConstructor
    @Getter
    @Builder
    static class Context{
        private final UUID userId;
        private final Booking booking;
    }

    private final BookingToBookingPreviewModelAdapterMapper previewModelMapper;

    @Override
    public BookingPreviewModel apply(final BookingToBookingPreviewMapper.Context context) {
        return map(context);
    }

    private BookingPreviewModel map(final BookingToBookingPreviewMapper.Context context) {
        BookingPreviewModel preview = previewModelMapper.apply(context.getBooking());
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(BookingController.class)
                .findOne(context.getUserId(),
                        context.getBooking().getFinancialLedger().getId(),
                        context.getBooking().getId())).withSelfRel();
        preview.add(selfLink);
        return preview;
    }

}
