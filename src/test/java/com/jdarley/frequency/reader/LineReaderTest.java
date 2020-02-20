package com.jdarley.frequency.reader;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;

public class LineReaderTest {

    @Test
    public void canStreamASingleLineFile() throws IOException {
        final List<String> contents = new LineReader("src/test/resources/example.txt").readToStream().collect(Collectors.toList());
        assertThat(contents.size(), equalTo(1));
    }

    @Test
    public void canStreamAZeroLengthFile() throws IOException {
        final List<String> contents = new LineReader("src/test/resources/zero_length.txt").readToStream().collect(Collectors.toList());
        assertThat(contents.size(), equalTo(0));
    }

    @Test
    public void canStreamAMultiLineFile() throws IOException {
        final List<String> contents = new LineReader("src/test/resources/sonnet.txt").readToStream().collect(Collectors.toList());
        assertThat(contents.size(), equalTo(4));
    }

    @Test
    public void throwsAnIOExceptionIfFileNotFound() {
        assertThrows(IOException.class, () -> {
            new LineReader("src/test/resources/not_found.txt").readToStream();
        });
    }
}