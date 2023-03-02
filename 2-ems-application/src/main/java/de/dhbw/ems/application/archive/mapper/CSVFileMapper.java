package de.dhbw.ems.application.archive.mapper;

import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.application.archive.core.TmpFileFactory;

import java.io.FileWriter;
import java.io.IOException;

public abstract class CSVFileMapper extends TmpFileFactory {

    private final CSVFactory csvFactory;

    protected CSVFileMapper(final CSVFactory csvFactory) {
        this.csvFactory = csvFactory;
    }

    public interface Callback {
        void onWrite(CSVFactory.CSVWriter writer) throws IOException;
    }

    protected TmpFile createCSVFile(String[] headers, Callback callBack){
        TmpFile tempFile = createTempCSVFile();
        try(FileWriter out = new FileWriter(tempFile.getFile())){
            CSVFactory.CSVWriter csvWriter = csvFactory.createWriter(out, headers);
            callBack.onWrite(csvWriter);
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
