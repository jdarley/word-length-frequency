package com.jdarley.frequency.result;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FrequencyLengthsTest {

    // Static methods
    @Test
    public void mostFrequentFromMapWithNullGivesEmptyOptional() {
        assertThat(FrequencyLengths.mostFrequentFromMap(null), equalTo(Optional.empty()));
    }

    @Test
    public void mostFrequentFromMapWithEmptyMapGivesEmptyOptional() {
        assertThat(FrequencyLengths.mostFrequentFromMap(Collections.emptyMap()), equalTo(Optional.empty()));
    }

    @Test
    public void mostFrequentFromMapWithSingleElementMapGivesFrequencyLength() {
        final Map<Integer, Long> input = new HashMap<>();
        input.put(1, 1L);

        final Optional<FrequencyLengths> expected = Optional.of(new FrequencyLengths(1L, Arrays.asList(1)));

        assertThat(FrequencyLengths.mostFrequentFromMap(input), equalTo(expected));
    }

    @Test
    public void mostFrequentFromMapWithMultipleElementsMapGivesCorrectFrequencyLength() {
        final Map<Integer, Long> input = new HashMap<>();
        input.put(1, 1L);
        input.put(2, 2L);

        final Optional<FrequencyLengths> expected = Optional.of(new FrequencyLengths(2L, Arrays.asList(2)));

        assertThat(FrequencyLengths.mostFrequentFromMap(input), equalTo(expected));
    }

    @Test
    public void mostFrequentFromMapWithMultipleElementsMapGivesCorrectFrequencyLengthWithMultipleLengths() {
        final Map<Integer, Long> input = new HashMap<>();
        input.put(1, 1L);
        input.put(2, 1L);

        final Optional<FrequencyLengths> expected = Optional.of(new FrequencyLengths(1L, Arrays.asList(1, 2)));

        assertThat(FrequencyLengths.mostFrequentFromMap(input), equalTo(expected));
    }

    @Test
    public void mostFrequentFromMapWithExampleDataYieldsCorrectResult() {
        final Map<Integer, Long> input = new HashMap<>();
        input.put(1, 1L);
        input.put(2, 1L);
        input.put(3, 1L);
        input.put(4, 2L);
        input.put(5, 2L);
        input.put(7, 1L);
        input.put(10, 1L);

        final Optional<FrequencyLengths> expected = Optional.of(new FrequencyLengths(2L, Arrays.asList(4, 5)));

        assertThat(FrequencyLengths.mostFrequentFromMap(input), equalTo(expected));
    }

    // Class methods
    @Test
    public void constructorWithNullFrequencyYieldsZeroFrequency() {
        FrequencyLengths input = new FrequencyLengths(null, null);
        assertThat(input.getFrequency(), equalTo(0L));
    }

    @Test
    public void constructorWithNullLengthsYieldsEmptyLengths() {
        FrequencyLengths input = new FrequencyLengths(null, null);
        assertThat(input.getLengths(), equalTo(Collections.emptyList()));
    }

    @Test
    public void constructorSortsLengths() {
        List<Integer> unsortedLengths = Arrays.asList(5, 10, 3, 1, 8);
        FrequencyLengths input = new FrequencyLengths(1L, unsortedLengths);
        assertThat(input.getLengths(), contains(1, 3, 5, 8, 10));
    }

    @Test
    public void lengthsToStringForSingleLengthRendersCorrectly() {
        FrequencyLengths input = new FrequencyLengths(1L, Arrays.asList(1));
        assertThat(input.lengthsToString(), equalTo("1"));
    }

    @Test
    public void lengthsToStringForTwoLengthsRendersCorrectly() {
        FrequencyLengths input = new FrequencyLengths(1L, Arrays.asList(1, 2));
        assertThat(input.lengthsToString(), equalTo("1 & 2"));
    }

    @Test
    public void lengthsToStringForThreeLengthsRendersCorrectly() {
        FrequencyLengths input = new FrequencyLengths(1L, Arrays.asList(1, 2, 3));
        assertThat(input.lengthsToString(), equalTo("1, 2 & 3"));
    }

    @Test
    public void lengthsToStringForManyLengthsRendersCorrectly() {
        FrequencyLengths input = new FrequencyLengths(1L, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        assertThat(input.lengthsToString(), equalTo("1, 2, 3, 4, 5, 6, 7, 8, 9 & 10"));
    }
}
