package com.jdarley.frequency;

import com.jdarley.frequency.pipeline.Pipeline;
import com.jdarley.frequency.reader.LineReader;
import com.jdarley.frequency.result.Result;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        final String filename = args[0];

        try (final Stream<String> lineStream = new LineReader(filename).readToStream()) {
            final Map<Integer, Long> data = new Pipeline(lineStream).execute();
            final Result result = new Result(data);
            System.out.println(result);
        } catch (IOException e) {
            System.out.println("Unable to open file: " + filename);
        }
    }

}
