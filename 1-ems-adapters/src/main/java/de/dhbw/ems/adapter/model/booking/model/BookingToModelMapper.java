package de.dhbw.ems.adapter.model.booking.model;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingToModelMapper implements BookingToBookingModelAdapterMapper {

    @Override
    public BookingModel apply(final BookingAggregate bookingAggregate) {
        return map(bookingAggregate);
    }

    private BookingModel map(final BookingAggregate bookingAggregate) {
        BookingModel.BookingModelBuilder builder = BookingModel.builder()
                .title(bookingAggregate.getBooking().getTitle())
                .amount(bookingAggregate.getBooking().getMoney().getAmount())
                .currencyType(bookingAggregate.getBooking().getMoney().getCurrencyType());
        return builder.build();
    }
}
