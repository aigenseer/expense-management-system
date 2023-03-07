package de.dhbw.ems.application.archive.mapper.bookings;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.mapper.CSVFactory;
import de.dhbw.ems.application.archive.mapper.CSVFileMapper;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import org.springframework.stereotype.Component;

@Component
public class BookingAggregatesToCSVFileMapper extends CSVFileMapper implements BookingAggregatesToCSVFileMapperFunction {

    public BookingAggregatesToCSVFileMapper(
            final CSVFactory csvFactory
    ){
        super(csvFactory);
    }

    @Override
    public TmpFile apply(final Iterable<BookingAggregate> bookingAggregates) {
        return map(bookingAggregates);
    }

    private TmpFile map(final Iterable<BookingAggregate> bookingAggregates) {
        String[] headers = {"Title", "Amount", "Currency Type", "Creator", "Category", "Creation Date"};
        return createCSVFile(headers, writer -> {
            for (BookingAggregate bookingAggregate : bookingAggregates) {
                writer.addRecord(
                        bookingAggregate.getTitle(),
                        bookingAggregate.getMoney().getAmount(),
                        bookingAggregate.getMoney().getCurrencyType(),
                        bookingAggregate.getCreator().getName(),
                        bookingAggregate.getCategoryAggregate().getTitle(),
                        bookingAggregate.getCreationDate().toString()
                );
            }
        });
    }
}
