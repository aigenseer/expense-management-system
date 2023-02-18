package de.dhbw.ems.application.archive.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@RequiredArgsConstructor
public class TmpFile implements Closeable {

    @Getter
    private final File file;

    @Override
    public void close() throws IOException {
        Files.deleteIfExists(file.toPath());
    }
}
