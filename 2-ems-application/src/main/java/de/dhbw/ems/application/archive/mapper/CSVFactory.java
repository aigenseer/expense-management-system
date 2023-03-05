package de.dhbw.ems.application.archive.mapper;

import com.sun.istack.NotNull;

import java.io.FileWriter;

public interface CSVFactory {

    interface CSVWriter {
        void addRecord(@NotNull Object... headers);
    }

    CSVWriter createWriter(@NotNull FileWriter fileWriter, @NotNull String... headers);

}
