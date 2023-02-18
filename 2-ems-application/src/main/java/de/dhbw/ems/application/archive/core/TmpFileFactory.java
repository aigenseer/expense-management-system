package de.dhbw.ems.application.archive.core;

import java.io.File;
import java.io.IOException;

public abstract class TmpFileFactory {

    protected TmpFile createTempCSVFile() {
        return createTempCSVFile(".csv");
    }

    protected TmpFile createTempCSVFile(String suffix) {
        try {
            return new TmpFile(File.createTempFile("ems-tmp-", suffix));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
