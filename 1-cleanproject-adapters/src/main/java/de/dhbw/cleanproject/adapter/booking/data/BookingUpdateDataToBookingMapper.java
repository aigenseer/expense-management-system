package de.dhbw.cleanproject.adapter.booking.data;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.cleanproject.abstractioncode.valueobject.money.Money;
import de.dhbw.cleanproject.domain.booking.Booking;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BookingUpdateDataToBookingMapper implements Function<Pair<Booking, BookingUpdateData>, Booking> {

    @Override
    public Booking apply(final Pair<Booking, BookingUpdateData> data) {
        return map(data);
    }

    private Booking map(final Pair<Booking, BookingUpdateData> pair) {
        Booking.BookingBuilder builder = Booking.builder();
        Booking booking = pair.getValue0();
        BookingUpdateData data = pair.getValue1();

        builder.id(booking.getId());
        builder.title(booking.getTitle());
        if (data.getTitle() != null) builder.title(data.getTitle());
        builder.money(booking.getMoney());
        try {
            CurrencyType currencyType = CurrencyType.valueOf(data.getCurrencyType());
            if(data.getCurrencyType() != null){
                Money money = new Money(data.getAmount(), currencyType);
                builder.money(money);
            }
        }catch (IllegalArgumentException|NullPointerException ignored){}
        return builder.build();
    }

}
