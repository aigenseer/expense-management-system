package de.dhbw.cleanproject.adapter.bookingcategory.data;

import de.dhbw.cleanproject.adapter.booking.preview.BookingPreviewCollectionModel;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BookingCategoryToBookingCategoryModelMapper implements Function<Pair<BookingCategory, BookingPreviewCollectionModel>, BookingCategoryModel> {

    @Override
    public BookingCategoryModel apply(final Pair<BookingCategory, BookingPreviewCollectionModel> pair) {
        return map(pair);
    }

    private BookingCategoryModel map(final Pair<BookingCategory, BookingPreviewCollectionModel> pair) {
        BookingCategory bookingCategory = (BookingCategory) pair.getValue(0);
        BookingPreviewCollectionModel bookingPreviewCollectionModel = (BookingPreviewCollectionModel) pair.getValue(1);

        BookingCategoryModel.BookingCategoryModelBuilder builder = BookingCategoryModel.builder()
                .title(bookingCategory.getTitle())
                .bookingPreviewCollectionModel(bookingPreviewCollectionModel);

        return builder.build();
    }
}
