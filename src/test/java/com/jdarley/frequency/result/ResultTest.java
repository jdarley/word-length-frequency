package com.jdarley.frequency.result;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ResultTest {

    @Test
    public void resultWithNullPrintsOnlyZeroWordCount() {
        Result result = new Result(null);
        assertThat(result.toString(), containsString("Word count = 0"));

        assertThat(result.toString(), not(containsString("Average word length =")));
        assertThat(result.toString(), not(containsString("Number of words of length")));
        assertThat(result.toString(), not(containsString("The most frequently occurring word length is")));
    }

    @Test
    public void resultWithEmptyMapPrintsOnlyZeroWordCount() {
        Result result = new Result(Collections.emptyMap());
        assertThat(result.toString(), containsString("Word count = 0"));

        assertThat(result.toString(), not(containsString("Average word length =")));
        assertThat(result.toString(), not(containsString("Number of words of length")));
        assertThat(result.toString(), not(containsString("The most frequently occurring word length is")));
    }

    @Test
    public void resultWithMapContainingZeroWordCountSkipsAverageWordLength() {
        Map<Integer, Long> input = new HashMap<>();
        input.put(1, 0L);

        Result result = new Result(input);
        assertThat(result.toString(), containsString("Word count = 0"));
        assertThat(result.toString(), containsString("Number of words of length 1 is 0"));
        assertThat(result.toString(), containsString("The most frequently occurring word length is 0, for word lengths of 1"));

        assertThat(result.toString(), not(containsString("Average word length =")));
    }

    @Test
    public void resultWithSingleMapEntryRendersCorrectly() {
        Map<Integer, Long> input = new HashMap<>();
        input.put(1, 1L);

        Result result = new Result(input);
        assertThat(result.toString(), containsString("Word count = 1"));
        assertThat(result.toString(), containsString("Average word length = 1.000"));
        assertThat(result.toString(), containsString("Number of words of length 1 is 1"));
        assertThat(result.toString(), containsString("The most frequently occurring word length is 1, for word lengths of 1"));
    }

    @Test
    public void resultWithRealisticMapRendersCorrectly() {
        Map<Integer, Long> input = new HashMap<>();
        input.put(1, 1L);
        input.put(2, 1L);
        input.put(3, 1L);
        input.put(4, 2L);
        input.put(5, 2L);
        input.put(7, 1L);
        input.put(10, 1L);

        Result result = new Result(input);
        assertThat(result.toString(), containsString("Word count = 9"));
        assertThat(result.toString(), containsString("Average word length = 4.556"));
        assertThat(result.toString(), containsString(
                "Number of words of length 1 is 1\n" +
                        "Number of words of length 2 is 1\n" +
                        "Number of words of length 3 is 1\n" +
                        "Number of words of length 4 is 2\n" +
                        "Number of words of length 5 is 2\n" +
                        "Number of words of length 7 is 1\n" +
                        "Number of words of length 10 is 1"));
        assertThat(result.toString(), containsString("The most frequently occurring word length is 2, for word lengths of 4 & 5"));
    }
}
