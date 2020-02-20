package com.jdarley.frequency.pipeline;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Functions {

    private static final Pattern WHITESPACE = Pattern.compile("\\s+");
    private static final Pattern PUNCTUATION = Pattern.compile("^[!():*;,.?\\-\"]+|[!():*;,.?\\-\"]+$");

    /**
     * Predicate for non-empty strings
     *
     * Predicate.not is available in Java 11
     */
    public static final Predicate<String> notEmpty = (s) -> !s.isEmpty();

    /**
     * Function to split a string by " " into a stream of string
     *
     * Filtered through notEmpty to produce an empty stream
     */
    public static final Function<String, Stream<String>> toWordStream = (s) -> WHITESPACE.splitAsStream(s).filter(notEmpty);

    /**
     * Function to trim leading and trailing punctuation from a string
     */
    public static final Function<String, String> stripPunctuation = (s) -> PUNCTUATION.matcher(s).replaceAll("");
}
