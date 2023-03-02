package de.dhbw.plugins.csv.factory.apache.commons;

import de.dhbw.ems.application.archive.mapper.CSVFactory;
import org.springframework.stereotype.Component;

import java.io.FileWriter;

@Component
public class ApacheCSVFactory implements CSVFactory {

    @Override
    public CSVWriter createWriter(FileWriter fileWriter, String... headers) {
        return new  ApacheCSVWriter(fileWriter, headers);
    }
}
