package de.dhbw.ems.adapter.model.booking.model;

import de.dhbw.ems.domain.booking.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingToModelMapper implements BookingToBookingModelAdapterMapper {

    @Override
    public BookingModel apply(final Booking booking) {
        return map(booking);
    }

    private BookingModel map(final Booking booking) {
        BookingModel.BookingModelBuilder builder = BookingModel.builder()
                .title(booking.getTitle())
                .amount(booking.getMoney().getAmount())
                .currencyType(booking.getMoney().getCurrencyType());
        return builder.build();
    }
}
