package de.dhbw.plugins.rest.mapper.controller.model;

import de.dhbw.ems.adapter.model.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.ems.adapter.model.booking.preview.BookingPreviewModel;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
@RequiredArgsConstructor
public class BookingsToBookingPreviewCollectionMapper implements Function<BookingsToBookingPreviewCollectionMapper.Context, BookingPreviewCollectionModel> {

    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class Context{
        private final UUID userId;
        private final Iterable<BookingAggregate> bookingAggregates;
    }

    private final BookingToBookingPreviewMapper bookingCategoryToBookingCategoryPreviewMapper;

    @Override
    public BookingPreviewCollectionModel apply(final BookingsToBookingPreviewCollectionMapper.Context context) {
        return map(context);
    }

    private BookingPreviewCollectionModel map(final BookingsToBookingPreviewCollectionMapper.Context context) {
        List<BookingPreviewModel> previewModels = StreamSupport.stream(context.getBookingAggregates().spliterator(), false)
                .map(bookingAggregate -> bookingCategoryToBookingCategoryPreviewMapper
                            .apply(BookingToBookingPreviewMapper.Context.builder()
                                    .userId(context.getUserId())
                                    .bookingAggregate(bookingAggregate)
                        .build())
                )
                .collect(Collectors.toList());
        return new BookingPreviewCollectionModel(previewModels);
    }

}
