package de.dhbw.plugins.mapper.bookingcategory;

import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewCollectionModel;
import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewModel;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;
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
public class BookingCategoriesToBookingCategoryPreviewCollectionMapper implements Function<BookingCategoriesToBookingCategoryPreviewCollectionMapper.Context, BookingCategoryPreviewCollectionModel> {

    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class Context{
        private final UUID userId;
        private final Iterable<BookingCategory> bookingCategories;
    }

    private final BookingCategoryToBookingCategoryPreviewMapper bookingCategoryToBookingCategoryPreviewMapper;

    @Override
    public BookingCategoryPreviewCollectionModel apply(final BookingCategoriesToBookingCategoryPreviewCollectionMapper.Context context) {
        return map(context);
    }

    private BookingCategoryPreviewCollectionModel map(final BookingCategoriesToBookingCategoryPreviewCollectionMapper.Context context) {
        List<BookingCategoryPreviewModel> previewModels = StreamSupport.stream(context.getBookingCategories().spliterator(), false)
                .map(bookingCategory -> bookingCategoryToBookingCategoryPreviewMapper
                            .apply(BookingCategoryToBookingCategoryPreviewMapper.Context.builder()
                                    .userId(context.getUserId())
                                    .bookingCategory(bookingCategory)
                        .build())
                )
                .collect(Collectors.toList());
        return new BookingCategoryPreviewCollectionModel(previewModels);
    }

}
