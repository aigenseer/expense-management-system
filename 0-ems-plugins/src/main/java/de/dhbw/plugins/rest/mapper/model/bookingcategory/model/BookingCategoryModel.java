package de.dhbw.plugins.rest.mapper.model.bookingcategory.model;

import de.dhbw.plugins.rest.mapper.model.booking.preview.BookingPreviewCollectionModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Builder
@Getter
public class BookingCategoryModel extends RepresentationModel<BookingCategoryModel>{
    private String title;
    @Setter
    private BookingPreviewCollectionModel bookingPreviewCollectionModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BookingCategoryModel that = (BookingCategoryModel) o;
        return Objects.equals(title, that.title) && Objects.equals(bookingPreviewCollectionModel, that.bookingPreviewCollectionModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, bookingPreviewCollectionModel);
    }
}
