package de.dhbw.ems.application.archive.mapper.bookingcategories;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.mapper.CSVFileMapper;
import de.dhbw.ems.domain.bookingcategory.BookingCategory;
import org.springframework.stereotype.Component;

@Component
public class BookingCategoriesToCSVFileMapper extends CSVFileMapper implements BookingCategoriesToCSVFileMapperFunction {

    @Override
    public TmpFile apply(final Iterable<BookingCategory> bookingCategories) {
        return map(bookingCategories);
    }

    private TmpFile map(final Iterable<BookingCategory> bookingCategories) {
        String[] headers = {"Name"};
        return createCSVFile(headers, printer -> {
            for (BookingCategory bookingCategory: bookingCategories) {
                printer.printRecord(bookingCategory.getTitle());
            }
        });
    }
}
