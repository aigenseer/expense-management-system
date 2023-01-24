package de.dhbw.cleanproject.adapter.booking.data;

import de.dhbw.cleanproject.domain.booking.Booking;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BookingUpdateDataToBookingMapper implements Function<Pair<Booking, IBookingData>, Booking> {

    private final BookingDataToBookingMapper bookingDataToBookingMapper;

    @Override
    public Booking apply(final Pair<Booking, IBookingData> data) {
        return map(data);
    }

    private Booking map(final Pair<Booking, IBookingData> pair) {
        Booking booking = pair.getValue0();
        Booking.BookingBuilder builder = Booking.builder();
        Booking updateBooking = bookingDataToBookingMapper.apply(Pair.with(pair.getValue1(), booking.getUser()));
        builder.id(booking.getId());
        builder.title(booking.getTitle());
        builder.creationDate(booking.getCreationDate());
        builder.user(booking.getUser());
        if (updateBooking.getTitle() != null) builder.title(updateBooking.getTitle());
        builder.money(booking.getMoney());
        if (updateBooking.getMoney() != null) builder.money(updateBooking.getMoney());
        return builder.build();
    }

}
