package de.dhbw.ems.application.archive.factory;


import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.core.TmpFileFactory;
import de.dhbw.ems.application.archive.mapper.bookingcategories.BookingCategoryAggregatesToCSVFileMapperFunction;
import de.dhbw.ems.application.archive.mapper.bookings.BookingAggregatesToCSVFileMapperFunction;
import de.dhbw.ems.application.archive.mapper.bookings.BookingUserReferencesToCSVFileMapperFunction;
import de.dhbw.ems.application.archive.mapper.financialledger.FinancialLedgerToCSVFileMapperFunction;
import de.dhbw.ems.application.archive.mapper.user.UsersToCSVFileMapperFunction;
import de.dhbw.ems.domain.booking.aggregate.BookingAggregate;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@RequiredArgsConstructor
public class FinancialLedgerArchiveFactory extends TmpFileFactory implements FinancialLedgerArchiveCreator {

    private final BookingAggregatesToCSVFileMapperFunction bookingAggregatesToCSVFileMapperFunction;
    private final UsersToCSVFileMapperFunction usersToCSVFileMapperFunction;
    private final FinancialLedgerToCSVFileMapperFunction financialLedgerToCSVFileMapperFunction;
    private final BookingUserReferencesToCSVFileMapperFunction bookingUserReferencesToCSVFileMapperFunction;
    private final BookingCategoryAggregatesToCSVFileMapperFunction bookingCategoryAggregatesToCSVFileMapperFunction;

    @Override
    public TmpFile createTmpZipArchive(FinancialLedger financialLedger) {
        TmpFile tmpFile = createTempCSVFile(".zip");
        try(ZipOutputStream out = new ZipOutputStream(new FileOutputStream(tmpFile.getFile()))){
            appendFileToZip(out, "booking-categories.csv", bookingCategoryAggregatesToCSVFileMapperFunction.apply(financialLedger.getBookingCategoriesAggregates()));
            appendFileToZip(out, "bookings.csv", bookingAggregatesToCSVFileMapperFunction.apply(financialLedger.getBookingAggregates()));
            appendFileToZip(out, "authorized-users.csv", usersToCSVFileMapperFunction.apply(financialLedger.getAuthorizedUser()));
            appendFileToZip(out, "financial-ledger.csv", financialLedgerToCSVFileMapperFunction.apply(financialLedger));
            for (BookingAggregate bookingAggregate : financialLedger.getBookingAggregates()) {
                appendFileToZip(out, "BookingReferences", String.format("%s.csv", bookingAggregate.getTitle()), bookingUserReferencesToCSVFileMapperFunction.apply(bookingAggregate));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tmpFile;
    }

    private void appendFileToZip(ZipOutputStream out, String fileName, TmpFile tmpFile) throws IOException {
        appendFileToZip(out, new ZipEntry(fileName), tmpFile);
    }

    private void appendFileToZip(ZipOutputStream out, String folderName, String fileName, TmpFile tmpFile) throws IOException {
        appendFileToZip(out, new ZipEntry(String.format("%s/%s", folderName, fileName)), tmpFile);
    }

    private void appendFileToZip(ZipOutputStream out, ZipEntry zipEntry, TmpFile tmpFile) throws IOException {
        try(FileInputStream in = new FileInputStream(tmpFile.getFile())){
            out.putNextEntry(zipEntry);
            byte[] b = new byte[1024];
            int count;
            while ((count = in.read(b)) > 0) {
                out.write(b, 0, count);
            }
        }finally{
            tmpFile.close();
        }
    }
}
