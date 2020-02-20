package com.jdarley.frequency.result;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimaps;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for encapsulating frequency and word length
 */
public class FrequencyLengths {

    private final Long frequency;
    private final List<Integer> lengths;

    /**
     * Creates a FrequentLengths containing the most frequent word lengths present
     * in the supplied Map.
     *
     * @param data The source {@link Map}
     * @return An optional {@link FrequencyLengths} containing the most
     * frequent word lengths and the frequency at which they occur
     */
    /*
    1. Inverts the map into a multimap, creating a map of frequencies to word lengths
    2. Extracts Optional<EntrySet> with the largest key, i.e. most frequently occurring
    3. Maps result to an Optional<FrequentLength>
     */
    public static Optional<FrequencyLengths> mostFrequentFromMap(final Map<Integer, Long> data) {
        if (data != null) {
            return Multimaps.invertFrom(
                    Multimaps.forMap(data),
                    ArrayListMultimap.create()
            ).asMap()
                    .entrySet()
                    .stream()
                    .max(Comparator.comparingLong(Map.Entry::getKey))
                    .map(e -> new FrequencyLengths(e.getKey(), e.getValue()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Constructor
     *
     * @param frequency The frequency
     * @param lengths The word lengths
     */
    public FrequencyLengths(final Long frequency, final Collection<Integer> lengths) {
        if (frequency == null) {
            this.frequency = 0L;
        } else {
            this.frequency = frequency;
        }

        if (lengths == null) {
            this.lengths = Collections.emptyList();
        } else {
            this.lengths = lengths
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

    public Long getFrequency() {
        return frequency;
    }

    public List<Integer> getLengths() {
        return lengths;
    }

    /**
     * Returns the number of lengths, correctly formatted, as a string
     *
     * @return A {@link String} representation of the lengths
     */
    public String lengthsToString() {
        final String last = lengths.get(lengths.size() - 1).toString();
        if (lengths.size() == 1) {
            return last;
        } else {
            final String first = lengths.stream().map(Object::toString).limit(lengths.size() - 1).collect( Collectors.joining( ", " ) );
            return first + " & " + last;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrequencyLengths that = (FrequencyLengths) o;
        return frequency.equals(that.frequency) &&
                lengths.equals(that.lengths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(frequency, lengths);
    }
}