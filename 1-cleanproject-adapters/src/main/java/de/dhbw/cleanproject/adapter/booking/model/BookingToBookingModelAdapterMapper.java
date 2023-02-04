package de.dhbw.cleanproject.adapter.booking.model;

import de.dhbw.cleanproject.domain.booking.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BookingToBookingModelAdapterMapper implements Function<Booking, BookingModel> {

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
