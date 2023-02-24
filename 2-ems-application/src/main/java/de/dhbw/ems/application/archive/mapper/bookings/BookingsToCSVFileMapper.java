package de.dhbw.ems.application.archive.mapper.bookings;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.mapper.CSVFileMapper;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import org.springframework.stereotype.Component;

@Component
public class BookingsToCSVFileMapper extends CSVFileMapper implements BookingsToCSVFileMapperFunction {

    @Override
    public TmpFile apply(final Iterable<BookingAggregate> bookings) {
        return map(bookings);
    }

    private TmpFile map(final Iterable<BookingAggregate> bookings) {
        String[] headers = {"Title", "Amount", "Currency Type", "Creator", "Category", "Creation Date"};
        return createCSVFile(headers, printer -> {
            for (BookingAggregate bookingAggregate : bookings) {
                printer.printRecord(
                        bookingAggregate.getBooking().getTitle(),
                        bookingAggregate.getBooking().getMoney().getAmount(),
                        bookingAggregate.getBooking().getMoney().getCurrencyType(),
                        bookingAggregate.getCreator().getName(),
                        bookingAggregate.getCategoryAggregate().getBookingCategory().getTitle(),
                        bookingAggregate.getCreationDate().toString()
                );
            }
        });
    }
}
