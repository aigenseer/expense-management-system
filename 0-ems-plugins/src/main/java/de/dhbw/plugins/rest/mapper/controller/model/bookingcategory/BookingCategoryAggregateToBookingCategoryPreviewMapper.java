package de.dhbw.plugins.rest.mapper.controller.model.bookingcategory;

import de.dhbw.plugins.rest.mapper.model.bookingcategory.preview.BookingCategoryAggregateToBookingCategoryPreviewModelAdapterMapper;
import de.dhbw.plugins.rest.mapper.model.bookingcategory.preview.BookingCategoryPreviewModel;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import de.dhbw.plugins.rest.controller.bookingcategory.BookingCategoryController;
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
public class BookingCategoryAggregateToBookingCategoryPreviewMapper implements Function<BookingCategoryAggregateToBookingCategoryPreviewMapper.Context, BookingCategoryPreviewModel> {

    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class Context{
        private final UUID userId;
        private final BookingCategoryAggregate bookingCategoryAggregate;
    }

    private final BookingCategoryAggregateToBookingCategoryPreviewModelAdapterMapper previewModelMapper;

    @Override
    public BookingCategoryPreviewModel apply(final BookingCategoryAggregateToBookingCategoryPreviewMapper.Context context) {
        return map(context);
    }

    private BookingCategoryPreviewModel map(final BookingCategoryAggregateToBookingCategoryPreviewMapper.Context context) {
        BookingCategoryPreviewModel preview = previewModelMapper.apply(context.getBookingCategoryAggregate());
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(BookingCategoryController.class)
                .findOne(context.getUserId(),
                        context.getBookingCategoryAggregate().getFinancialLedgerId(),
                        context.getBookingCategoryAggregate().getId())).withSelfRel();
        preview.add(selfLink);
        return preview;
    }

}
