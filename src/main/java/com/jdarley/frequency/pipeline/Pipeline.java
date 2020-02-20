package com.jdarley.frequency.pipeline;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jdarley.frequency.pipeline.Functions.*;

/**
 * Pipeline for generating a Map of word lengths to frequency counts
 */
public class Pipeline {

    private final Stream<String> stream;

    /**
     * Constructor
     *
     * @param stream A {@link Stream} on which to execute
     */
    public Pipeline(final Stream<String> stream) {
        this.stream = stream;
    }

    /**
     * Executes the pipeline
     *
     * @return A {@link Map} containing word lengths to frequency count
     */
    public Map<Integer, Long> execute() {
        return stream
                .flatMap(toWordStream)
                .map(stripPunctuation)
                .map(String::trim)
                .filter(notEmpty)
                .collect(
                        Collectors.groupingBy(
                                String::length,
                                Collectors.counting())
                );
    }
}
