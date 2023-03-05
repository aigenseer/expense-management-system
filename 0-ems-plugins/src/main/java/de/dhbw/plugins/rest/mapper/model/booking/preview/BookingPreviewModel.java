package de.dhbw.plugins.rest.mapper.model.booking.preview;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Builder
@Getter
public class BookingPreviewModel extends RepresentationModel<BookingPreviewModel>{
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BookingPreviewModel that = (BookingPreviewModel) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
