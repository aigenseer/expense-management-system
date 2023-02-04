package de.dhbw.plugins.mapper.booking;

import de.dhbw.cleanproject.adapter.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.booking.preview.BookingPreviewModel;
import de.dhbw.cleanproject.domain.booking.Booking;
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
        private final Iterable<Booking> bookings;
    }

    private final BookingToBookingPreviewMapper bookingCategoryToBookingCategoryPreviewMapper;

    @Override
    public BookingPreviewCollectionModel apply(final BookingsToBookingPreviewCollectionMapper.Context context) {
        return map(context);
    }

    private BookingPreviewCollectionModel map(final BookingsToBookingPreviewCollectionMapper.Context context) {
        List<BookingPreviewModel> previewModels = StreamSupport.stream(context.getBookings().spliterator(), false)
                .map(booking -> bookingCategoryToBookingCategoryPreviewMapper
                            .apply(BookingToBookingPreviewMapper.Context.builder()
                                    .userId(context.getUserId())
                                    .booking(booking)
                        .build())
                )
                .collect(Collectors.toList());
        return new BookingPreviewCollectionModel(previewModels);
    }

}
