package de.dhbw.cleanproject.adapter.booking.model;

import de.dhbw.cleanproject.adapter.bookingcategory.preview.BookingCategoryPreviewModel;
import de.dhbw.cleanproject.adapter.user.preview.UserPreview;
import de.dhbw.cleanproject.adapter.user.preview.UserPreviewCollectionModel;
import de.dhbw.cleanproject.adapter.user.preview.UserToUserPreviewModelMapper;
import de.dhbw.cleanproject.domain.booking.Booking;
import lombok.RequiredArgsConstructor;
import org.javatuples.Triplet;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BookingToBookingModelMapper implements Function<Triplet<Booking, UserPreviewCollectionModel, BookingCategoryPreviewModel>, BookingModel> {

    private final UserToUserPreviewModelMapper userToUserPreviewModelMapper;

    @Override
    public BookingModel apply(final Triplet<Booking, UserPreviewCollectionModel, BookingCategoryPreviewModel> pair) {
        return map(pair);
    }

    private BookingModel map(final Triplet<Booking, UserPreviewCollectionModel, BookingCategoryPreviewModel> pair) {
        Booking booking = (Booking) pair.getValue(0);
        UserPreviewCollectionModel referencedUserPreviewCollectionModel = (UserPreviewCollectionModel) pair.getValue(1);
        BookingCategoryPreviewModel bookingCategoryPreviewModel = (BookingCategoryPreviewModel) pair.getValue(2);
        UserPreview userPreview = userToUserPreviewModelMapper.apply(booking.getUser());

        BookingModel.BookingModelBuilder builder = BookingModel.builder()
                .title(booking.getTitle())
                .amount(booking.getMoney().getAmount())
                .currencyType(booking.getMoney().getCurrencyType())
                .creator(userPreview)
                .referencedUsers(referencedUserPreviewCollectionModel)
                .category(bookingCategoryPreviewModel);
        return builder.build();
    }
}
