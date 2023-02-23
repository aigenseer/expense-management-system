package de.dhbw.ems.application.archive.mapper.bookingcategories;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.domain.bookingcategory.entity.BookingCategory;

import java.util.function.Function;

public interface BookingCategoriesToCSVFileMapperFunction extends Function<Iterable<BookingCategory>, TmpFile> {

}