package de.dhbw.ems.adapter.model.booking.model;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.adapter.model.bookingcategory.preview.BookingCategoryPreviewModel;
import de.dhbw.ems.adapter.model.user.preview.UserPreview;
import de.dhbw.ems.adapter.model.user.preview.UserPreviewCollectionModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Builder
@Getter
public class BookingModel extends RepresentationModel<BookingModel>{
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BookingModel that = (BookingModel) o;
        return Objects.equals(title, that.title) && Objects.equals(creator, that.creator) && Objects.equals(amount, that.amount) && currencyType == that.currencyType && Objects.equals(referencedUsers, that.referencedUsers) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, creator, amount, currencyType, referencedUsers, category);
    }

    @Setter
    private UserPreview creator;
    private Double amount;
    private CurrencyType currencyType;
    @Setter
    private UserPreviewCollectionModel referencedUsers;
    @Setter
    private BookingCategoryPreviewModel category;
}
