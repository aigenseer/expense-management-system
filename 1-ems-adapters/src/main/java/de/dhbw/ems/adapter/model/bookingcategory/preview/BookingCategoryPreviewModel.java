package de.dhbw.ems.adapter.model.bookingcategory.preview;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Builder
@Getter
public class BookingCategoryPreviewModel extends RepresentationModel<BookingCategoryPreviewModel>{
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BookingCategoryPreviewModel that = (BookingCategoryPreviewModel) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title);
    }
}
