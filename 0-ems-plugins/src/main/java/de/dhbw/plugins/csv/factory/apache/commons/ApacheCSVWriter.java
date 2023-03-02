package de.dhbw.plugins.csv.factory.apache.commons;

import com.sun.istack.NotNull;
import de.dhbw.ems.application.archive.mapper.CSVFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;

public class ApacheCSVWriter implements CSVFactory.CSVWriter {

    private final CSVPrinter printer;

    public ApacheCSVWriter(final FileWriter fileWriter, final @NotNull String... headers){
        this.printer = createCSVPrinter(fileWriter, headers);
    }

    public CSVPrinter createCSVPrinter(final FileWriter fileWriter, final @NotNull String... headers){
        try {
            return new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(headers));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addRecord(Object... values) {
        try {
            printer.printRecord(values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
