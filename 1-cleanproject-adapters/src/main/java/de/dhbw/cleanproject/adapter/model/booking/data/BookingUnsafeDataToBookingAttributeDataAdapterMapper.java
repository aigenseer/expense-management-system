package de.dhbw.cleanproject.adapter.model.booking.data;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.cleanproject.abstractioncode.valueobject.money.Money;
import de.dhbw.cleanproject.application.booking.BookingAttributeData;
import de.dhbw.cleanproject.application.bookingcategory.BookingCategoryApplicationService;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BookingUnsafeDataToBookingAttributeDataAdapterMapper implements Function<IBookingUnsafeData, BookingAttributeData> {

    private final BookingCategoryApplicationService bookingCategoryApplicationService;

    @Override
    public BookingAttributeData apply(final IBookingUnsafeData data) {
        return map(data);
    }

    private BookingAttributeData map(final IBookingUnsafeData data) {
        BookingAttributeData.BookingAttributeDataBuilder builder = BookingAttributeData.builder();
        if (data.getTitle() != null)
            builder.title(data.getTitle());
        try {
            CurrencyType currencyType = CurrencyType.valueOf(data.getCurrencyType());
            if(data.getCurrencyType() != null){
                Money money = new Money(data.getAmount(), currencyType);
                builder.money(money);
            }
        }catch (IllegalArgumentException|NullPointerException ignored){}
        if(data.getBookingCategoryId() != null){
            UUID bookingCategoryId = UUID.fromString(data.getBookingCategoryId());
            Optional<BookingCategory> optionalBooking = bookingCategoryApplicationService.findById(bookingCategoryId);
            optionalBooking.ifPresent(builder::bookingCategory);
        }
        return builder.build();
    }

}
