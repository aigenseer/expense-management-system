package de.dhbw.plugins.rest.mapper.model.booking.model;

import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingAggregateToModelMapper implements BookingAggregateToBookingModelMapper {

    @Override
    public BookingModel apply(final BookingAggregate bookingAggregate) {
        return map(bookingAggregate);
    }

    private BookingModel map(final BookingAggregate bookingAggregate) {
        BookingModel.BookingModelBuilder builder = BookingModel.builder()
                .title(bookingAggregate.getTitle())
                .amount(bookingAggregate.getMoney().getAmount())
                .currencyType(bookingAggregate.getMoney().getCurrencyType());
        return builder.build();
    }
}
