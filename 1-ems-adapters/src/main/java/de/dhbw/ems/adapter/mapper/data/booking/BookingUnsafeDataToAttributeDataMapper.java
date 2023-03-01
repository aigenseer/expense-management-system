package de.dhbw.ems.adapter.mapper.data.booking;

import de.dhbw.ems.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.ems.abstractioncode.valueobject.money.Money;
import de.dhbw.ems.application.booking.data.BookingAggregateAttributeData;
import de.dhbw.ems.application.bookingcategory.aggregate.BookingCategoryDomainServicePort;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookingUnsafeDataToAttributeDataMapper implements BookingUnsafeDataToBookingAttributeDataAdapterMapper {

    private final BookingCategoryDomainServicePort bookingCategoryDomainServicePort;

    @Override
    public BookingAggregateAttributeData apply(final IBookingUnsafeData data) {
        return map(data);
    }

    private BookingAggregateAttributeData map(final IBookingUnsafeData data) {
        BookingAggregateAttributeData.BookingAggregateAttributeDataBuilder builder = BookingAggregateAttributeData.builder();
        if (data.getTitle() != null)
            builder.title(data.getTitle());
        try {
            CurrencyType currencyType = CurrencyType.valueOf(data.getCurrencyType());
            if(data.getCurrencyType() != null){
                Money money = new Money(data.getAmount(), currencyType);
                builder.money(money);
            }
        }catch (IllegalArgumentException|NullPointerException ignored){}
        if(data.getBookingCategoryAggregateId() != null){
            UUID bookingCategoryId = UUID.fromString(data.getBookingCategoryAggregateId());
            Optional<BookingCategoryAggregate> optionalBookingCategoryAggregate = bookingCategoryDomainServicePort.findById(bookingCategoryId);
            optionalBookingCategoryAggregate.ifPresent(builder::bookingCategoryAggregate);
        }
        return builder.build();
    }

}
