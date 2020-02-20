package com.jdarley.frequency.result;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jdarley.frequency.result.Functions.*;

/**
 * Class to hold word length frequency details prior to rendering
 */
public class Result {

    private final Optional<Long> wordCount;
    private final Optional<Double> averageWordLength;
    private final Map<Integer, Long> sortedData;
    private final Optional<FrequencyLengths> mostFrequentWordLengths;

    /**
     * Constructor
     *
     * @param input A {@link Map} containing word lengths to frequency count
     */
    public Result(final Map<Integer, Long> input) {
        if (input == null) {
            wordCount = Optional.empty();
            averageWordLength = Optional.empty();
            sortedData = Collections.emptyMap();
            mostFrequentWordLengths = Optional.empty();
        } else {
            wordCount = calculateWordCount(input);
            averageWordLength = calculateAverageWordLength(input, wordCount);
            sortedData = sortData(input);
            mostFrequentWordLengths = FrequencyLengths.mostFrequentFromMap(input);
        }
    }

    /**
     * Counts all of the words in the supplied Map
     *
     * @param input A {@link Map} containing word lengths to frequency count
     * @return An {@link Optional}<{@link Long}> containing the word count
     */
    private Optional<Long> calculateWordCount(final Map<Integer, Long> input) {
        return input.values().stream().reduce(Long::sum);
    }

    /**
     * Generates an average word length, based on the supplied Map and word count.
     *
     * @param input A {@link Map} containing word lengths to frequency count
     * @param wordCount A {@link Long} denoting a count of all words in the map
     * @return A {@link Optional}<{@link Double}> containing the average word length
     */
    private Optional<Double> calculateAverageWordLength(final Map<Integer, Long> input, final Optional<Long> wordCount) {
        final Optional<Long> totalWordLength = input.entrySet().stream()
                .map(product)
                .reduce(Long::sum);

        if (wordCount.isPresent() && wordCount.get() != 0 && totalWordLength.isPresent()) {
            return Optional.of(totalWordLength.get() / (double) wordCount.get());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Sorts the input Map (by key) into a LinkedHashMap, creating
     * an ordered map.
     *
     * @param data A {@link Map} containing word lengths to frequency count
     * @return A sorted {@link LinkedHashMap} containing word lengths to frequency count
     */
    private Map<Integer, Long> sortData(final Map<Integer, Long> data) {
        return data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue,
                                LinkedHashMap::new
                        )
                );
    }

    @Override
    public String toString() {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);

        printWriter.println("Word count = " + wordCount.orElse(0L));
        averageWordLength.ifPresent(aDouble -> printWriter.println("Average word length = " + String.format("%.3f", aDouble)));
        sortedData.forEach((key, value) -> printWriter.println(String.format("Number of words of length %d is %d", key, value)));
        mostFrequentWordLengths.ifPresent(fl -> printWriter.println("The most frequently occurring word length is " + fl.getFrequency() + ", for word lengths of " + fl.lengthsToString()));

        return stringWriter.toString();
    }
}
