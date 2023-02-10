package de.dhbw.plugins.mapper.bookingcategory;

import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewModel;
import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryToBookingCategoryPreviewModelAdapterMapper;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import de.dhbw.plugins.rest.bookingcategory.BookingCategoryController;
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
public class BookingCategoryToBookingCategoryPreviewMapper implements Function<BookingCategoryToBookingCategoryPreviewMapper.Context, BookingCategoryPreviewModel> {

    @RequiredArgsConstructor
    @Getter
    @Builder
    public static class Context{
        private final UUID userId;
        private final BookingCategory bookingCategory;
    }

    private final BookingCategoryToBookingCategoryPreviewModelAdapterMapper previewModelMapper;

    @Override
    public BookingCategoryPreviewModel apply(final BookingCategoryToBookingCategoryPreviewMapper.Context context) {
        return map(context);
    }

    private BookingCategoryPreviewModel map(final BookingCategoryToBookingCategoryPreviewMapper.Context context) {
        BookingCategoryPreviewModel preview = previewModelMapper.apply(context.getBookingCategory());
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(BookingCategoryController.class)
                .findOne(context.getUserId(),
                        context.getBookingCategory().getFinancialLedger().getId(),
                        context.getBookingCategory().getId())).withSelfRel();
        preview.add(selfLink);
        return preview;
    }

}
