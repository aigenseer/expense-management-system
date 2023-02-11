package de.dhbw.plugins.mapper.booking;

import de.dhbw.ems.adapter.model.booking.model.BookingModel;
import de.dhbw.ems.adapter.model.booking.model.BookingToBookingModelAdapterMapper;
import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewModel;
import de.dhbw.ems.adapter.model.user.preview.UserPreview;
import de.dhbw.ems.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.plugins.mapper.bookingcategory.BookingCategoryToBookingCategoryPreviewMapper;
import de.dhbw.plugins.mapper.user.UserToUserPreviewMapper;
import de.dhbw.plugins.mapper.user.UsersToUserPreviewCollectionMapper;
import de.dhbw.plugins.rest.booking.BookingController;
import de.dhbw.plugins.rest.booking.users.BookingReferencedUsersController;
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
public class BookingToBookingModelMapper implements Function<BookingToBookingModelMapper.Context, BookingModel> {

    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class Context{
        private final UUID userId;
        private final Booking booking;
    }

    private final BookingToBookingModelAdapterMapper bookingToBookingModelAdapterMapper;
    private final UsersToUserPreviewCollectionMapper usersToUserPreviewCollectionMapper;
    private final BookingCategoryToBookingCategoryPreviewMapper bookingCategoryToBookingCategoryPreviewMapper;
    private final UserToUserPreviewMapper userToUserPreviewMapper;

    @Override
    public BookingModel apply(final BookingToBookingModelMapper.Context context) {
        return map(context);
    }

    private BookingModel map(final BookingToBookingModelMapper.Context context) {
        UUID userId = context.getUserId();
        Booking booking = context.getBooking();
        BookingModel model = bookingToBookingModelAdapterMapper.apply(context.getBooking());

        UserPreview creatorPreview = userToUserPreviewMapper.apply(booking.getCreator());
        model.setCreator(creatorPreview);

        UserPreviewCollectionModel referencedUserPreviewCollectionModel = usersToUserPreviewCollectionMapper.apply(booking.getReferencedUsers());
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(BookingReferencedUsersController.class).listAll(userId, booking.getFinancialLedgerId(), booking.getId())).withSelfRel();
        referencedUserPreviewCollectionModel.add(selfLink);
        model.setReferencedUsers(referencedUserPreviewCollectionModel);

        BookingCategoryPreviewModel bookingCategoryPreviewModel = null;
        if(booking.getCategory() != null){
            bookingCategoryPreviewModel = bookingCategoryToBookingCategoryPreviewMapper
                    .apply(BookingCategoryToBookingCategoryPreviewMapper.Context.builder()
                            .userId(userId)
                            .bookingCategory(booking.getCategory())
                            .build());
        }
        model.setCategory(bookingCategoryPreviewModel);

        selfLink = WebMvcLinkBuilder.linkTo(methodOn(BookingController.class)
                .findOne(context.getUserId(),
                        context.getBooking().getFinancialLedgerId(),
                        context.getBooking().getId())).withSelfRel();
        model.add(selfLink);
        return model;
    }

}
