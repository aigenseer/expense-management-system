package de.dhbw.ems.application.archive.mapper.bookings;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;

import java.util.function.Function;

public interface BookingUserReferencesToCSVFileMapperFunction extends Function<BookingAggregate, TmpFile> {

}