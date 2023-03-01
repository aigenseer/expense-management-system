package de.dhbw.plugins.mapper.bookingcategory.model;

import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewModel;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
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
public class BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper implements Function<BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper.Context, BookingCategoryPreviewCollectionModel> {

    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class Context{
        private final UUID userId;
        private final Iterable<BookingCategoryAggregate> bookingCategoryAggregates;
    }

    private final BookingCategoryAggregateToBookingCategoryPreviewMapper bookingCategoryToBookingCategoryPreviewMapper;

    @Override
    public BookingCategoryPreviewCollectionModel apply(final BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper.Context context) {
        return map(context);
    }

    private BookingCategoryPreviewCollectionModel map(final BookingCategoryAggregateToBookingCategoryPreviewCollectionMapper.Context context) {
        List<BookingCategoryPreviewModel> previewModels = StreamSupport.stream(context.getBookingCategoryAggregates().spliterator(), false)
                .map(bookingCategoryAggregate -> bookingCategoryToBookingCategoryPreviewMapper
                            .apply(BookingCategoryAggregateToBookingCategoryPreviewMapper.Context.builder()
                                    .userId(context.getUserId())
                                    .bookingCategoryAggregate(bookingCategoryAggregate)
                        .build())
                )
                .collect(Collectors.toList());
        return new BookingCategoryPreviewCollectionModel(previewModels);
    }

}
