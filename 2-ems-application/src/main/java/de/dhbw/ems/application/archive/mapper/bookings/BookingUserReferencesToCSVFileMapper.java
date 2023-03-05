package de.dhbw.ems.application.archive.mapper.bookings;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.mapper.CSVFactory;
import de.dhbw.ems.application.archive.mapper.CSVFileMapper;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class BookingUserReferencesToCSVFileMapper extends CSVFileMapper implements BookingUserReferencesToCSVFileMapperFunction {

    public BookingUserReferencesToCSVFileMapper(
            final CSVFactory csvFactory
    ){
        super(csvFactory);
    }

    @Override
    public TmpFile apply(final BookingAggregate bookingAggregate) {
        return map(bookingAggregate);
    }

    private TmpFile map(final BookingAggregate bookingAggregate) {
        String[] headers = {"User-Name"};
        return createCSVFile(headers, writer -> {
            for (User user: bookingAggregate.getReferencedUsers()) {
                writer.addRecord(user.getName());
            }
        });
    }
}
