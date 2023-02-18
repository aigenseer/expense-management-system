package de.dhbw.ems.application.archive.mapper;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.core.TmpFileFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;

public abstract class CSVFileMapper extends TmpFileFactory {



    public interface Callback {
        void onCSVPrinter(CSVPrinter out) throws IOException;
    }

    protected TmpFile createCSVFile(String[] headers, Callback callBack){
        TmpFile tempFile = createTempCSVFile();
        try(FileWriter out = new FileWriter(tempFile.getFile())){
            CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers));
            callBack.onCSVPrinter(printer);
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
