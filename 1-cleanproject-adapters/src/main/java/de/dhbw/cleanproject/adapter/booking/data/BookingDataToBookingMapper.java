package de.dhbw.cleanproject.adapter.booking.data;

import de.dhbw.cleanproject.abstractioncode.valueobject.money.CurrencyType;
import de.dhbw.cleanproject.abstractioncode.valueobject.money.Money;
import de.dhbw.cleanproject.application.BookingCategoryApplicationService;
import de.dhbw.cleanproject.domain.booking.Booking;
import de.dhbw.cleanproject.domain.bookingcategory.BookingCategory;
import de.dhbw.cleanproject.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.javatuples.Triplet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BookingDataToBookingMapper implements Function<Triplet<IBookingData, UUID, User>, Booking> {

    private final BookingCategoryApplicationService bookingCategoryApplicationService;

    @Override
    public Booking apply(final Triplet<IBookingData, UUID, User> triplet) {
        return map(triplet);
    }

    private Booking map(final Triplet<IBookingData, UUID, User> triplet) {
        IBookingData data = triplet.getValue0();
        UUID financialLedgerId = triplet.getValue1();
        User user = triplet.getValue2();
        Booking.BookingBuilder builder = Booking.builder();
        builder.id(UUID.randomUUID());
        builder.title(data.getTitle());
        builder.financialLedgerId(financialLedgerId);
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
        if(data.getBookingCategoryId() != null){
            UUID bookingCategoryId = UUID.fromString(data.getBookingCategoryId());
            Optional<BookingCategory> optionalBooking = bookingCategoryApplicationService.findById(bookingCategoryId);
            optionalBooking.ifPresent(builder::category);
        }

        return builder.build();
    }

}
