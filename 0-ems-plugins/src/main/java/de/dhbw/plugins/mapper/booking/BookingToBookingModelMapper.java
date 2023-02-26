package de.dhbw.plugins.mapper.booking;

import de.dhbw.ems.adapter.model.booking.model.BookingModel;
import de.dhbw.ems.adapter.model.booking.model.BookingToBookingModelAdapterMapper;
import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewModel;
import de.dhbw.ems.adapter.model.user.preview.UserPreview;
import de.dhbw.ems.adapter.model.user.preview.UserPreviewCollectionModel;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.plugins.mapper.bookingcategory.BookingCategoryAggregateToBookingCategoryPreviewMapper;
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
        private final BookingAggregate bookingAggregate;
    }

    private final BookingToBookingModelAdapterMapper bookingToBookingModelAdapterMapper;
    private final UsersToUserPreviewCollectionMapper usersToUserPreviewCollectionMapper;
    private final BookingCategoryAggregateToBookingCategoryPreviewMapper bookingCategoryToBookingCategoryPreviewMapper;
    private final UserToUserPreviewMapper userToUserPreviewMapper;

    @Override
    public BookingModel apply(final BookingToBookingModelMapper.Context context) {
        return map(context);
    }

    private BookingModel map(final BookingToBookingModelMapper.Context context) {
        UUID userId = context.getUserId();
        BookingAggregate bookingAggregate = context.getBookingAggregate();
        BookingModel model = bookingToBookingModelAdapterMapper.apply(context.getBookingAggregate());

        UserPreview creatorPreview = userToUserPreviewMapper.apply(bookingAggregate.getCreator());
        model.setCreator(creatorPreview);

        UserPreviewCollectionModel referencedUserPreviewCollectionModel = usersToUserPreviewCollectionMapper.apply(bookingAggregate.getReferencedUsers());
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(BookingReferencedUsersController.class).listAll(userId, bookingAggregate.getFinancialLedgerId(), bookingAggregate.getBooking().getId())).withSelfRel();
        referencedUserPreviewCollectionModel.add(selfLink);
        model.setReferencedUsers(referencedUserPreviewCollectionModel);

        BookingCategoryPreviewModel bookingCategoryPreviewModel = null;
        if(bookingAggregate.getCategoryAggregate() != null){
            bookingCategoryPreviewModel = bookingCategoryToBookingCategoryPreviewMapper
                    .apply(BookingCategoryAggregateToBookingCategoryPreviewMapper.Context.builder()
                            .userId(userId)
                            .bookingCategoryAggregate(bookingAggregate.getCategoryAggregate())
                            .build());
        }
        model.setCategory(bookingCategoryPreviewModel);

        selfLink = WebMvcLinkBuilder.linkTo(methodOn(BookingController.class)
                .findOne(context.getUserId(),
                        context.getBookingAggregate().getFinancialLedgerId(),
                        context.getBookingAggregate().getId())).withSelfRel();
        model.add(selfLink);
        return model;
    }

}
