package de.dhbw.cleanproject.adapter.booking.data;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.cleanproject.abstractioncode.valueobject.money.Money;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BookingDataToBookingMapper implements Function<Pair<IBookingData, User>, Booking> {

    @Override
    public Booking apply(final Pair<IBookingData, User> pair) {
        return map(pair);
    }

    private Booking map(final Pair<IBookingData, User> pair) {
        IBookingData data = pair.getValue0();
        User user = pair.getValue1();
        Booking.BookingBuilder builder = Booking.builder();
        builder.id(UUID.randomUUID());
        builder.title(data.getTitle());
        builder.user(user);
        builder.creationDate(LocalDate.now());
        if (data.getTitle() != null) builder.title(data.getTitle());
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
