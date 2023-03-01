package de.dhbw.ems.application.archive.mapper.bookingcategories;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.domain.bookingcategory.aggregate.BookingCategoryAggregate;

import java.util.function.Function;

public interface BookingCategoryAggregatesToCSVFileMapperFunction extends Function<Iterable<BookingCategoryAggregate>, TmpFile> {

}