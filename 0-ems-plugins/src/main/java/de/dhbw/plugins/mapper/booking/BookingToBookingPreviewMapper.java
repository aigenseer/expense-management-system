package de.dhbw.plugins.mapper.booking;

import de.dhbw.ems.adapter.model.booking.preview.BookingPreviewModel;
import de.dhbw.ems.adapter.model.booking.preview.BookingAggregateToBookingPreviewModelAdapterMapper;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
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
        private final BookingAggregate bookingAggregate;
    }

    private final BookingAggregateToBookingPreviewModelAdapterMapper previewModelMapper;

    @Override
    public BookingPreviewModel apply(final BookingToBookingPreviewMapper.Context context) {
        return map(context);
    }

    private BookingPreviewModel map(final BookingToBookingPreviewMapper.Context context) {
        BookingPreviewModel preview = previewModelMapper.apply(context.getBookingAggregate());
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(BookingController.class)
                .findOne(context.getUserId(),
                        context.getBookingAggregate().getFinancialLedgerAggregate().getId(),
                        context.getBookingAggregate().getBooking().getId())).withSelfRel();
        preview.add(selfLink);
        return preview;
    }

}
