package com.jdarley.frequency.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class LineReader {

    private final String filename;

    public LineReader(final String filename) {
        this.filename = filename;
    }

    public Stream<String> readToStream() throws IOException {
        return Files.lines(Paths.get(filename));
    }

}
