package de.dhbw.ems.application.archive.mapper.bookingcategories;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.mapper.CSVFileMapper;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;
import org.springframework.stereotype.Component;

@Component
public class BookingCategoryAggregatesToCSVFileMapper extends CSVFileMapper implements BookingCategoryAggregatesToCSVFileMapperFunction {

    @Override
    public TmpFile apply(final Iterable<BookingCategoryAggregate> bookingCategoriesAggregates) {
        return map(bookingCategoriesAggregates);
    }

    private TmpFile map(final Iterable<BookingCategoryAggregate> bookingCategoriesAggregates) {
        String[] headers = {"Name"};
        return createCSVFile(headers, printer -> {
            for (BookingCategoryAggregate bookingCategoryAggregate: bookingCategoriesAggregates) {
                printer.printRecord(bookingCategoryAggregate.getBookingCategory().getTitle());
            }
        });
    }
}
