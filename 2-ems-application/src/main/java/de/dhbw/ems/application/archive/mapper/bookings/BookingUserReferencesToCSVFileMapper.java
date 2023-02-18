package de.dhbw.ems.application.archive.mapper.bookings;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.mapper.CSVFileMapper;
import de.dhbw.ems.domain.booking.Booking;
import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class BookingUserReferencesToCSVFileMapper extends CSVFileMapper implements BookingUserReferencesToCSVFileMapperFunction {

    @Override
    public TmpFile apply(final Booking booking) {
        return map(booking);
    }

    private TmpFile map(final Booking booking) {
        String[] headers = {"User-Name"};
        return createCSVFile(headers, printer -> {
            for (User user: booking.getReferencedUsers()) {
                printer.printRecord(user.getName());
            }
        });
    }
}
