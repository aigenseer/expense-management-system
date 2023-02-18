package de.dhbw.ems.application.archive.mapper.bookings;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.mapper.CSVFileMapper;
import de.dhbw.ems.domain.booking.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingsToCSVFileMapper extends CSVFileMapper implements BookingsToCSVFileMapperFunction {

    @Override
    public TmpFile apply(final Iterable<Booking> bookings) {
        return map(bookings);
    }

    private TmpFile map(final Iterable<Booking> bookings) {
        String[] headers = {"Title", "Amount", "Currency Type", "Creator", "Category", "Creation Date"};
        return createCSVFile(headers, printer -> {
            for (Booking booking: bookings) {
                printer.printRecord(
                        booking.getTitle(),
                        booking.getMoney().getAmount(),
                        booking.getMoney().getCurrencyType(),
                        booking.getCreator().getName(),
                        booking.getCategory().getTitle(),
                        booking.getCreationDate().toString()
                );
            }
        });
    }
}
