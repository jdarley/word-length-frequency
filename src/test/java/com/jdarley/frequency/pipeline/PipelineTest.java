package com.jdarley.frequency.pipeline;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PipelineTest {

    @Test
    public void emptyString() {
        final Stream<String> input = Stream.of("");
        final Pipeline pipeline = new Pipeline(input);

        assertThat(pipeline.execute(), is(Collections.EMPTY_MAP));
    }

    @Test
    public void emptyList() {
        final Stream<String> input = new ArrayList<String>().stream();
        final Pipeline pipeline = new Pipeline(input);

        assertThat(pipeline.execute(), is(Collections.EMPTY_MAP));
    }

    @Test
    public void singleWord() {
        final Stream<String> input = Stream.of("example");
        final Pipeline pipeline = new Pipeline(input);

        Map<Integer, Long> expected = new HashMap<>();
        expected.put(7, 1L);

        assertThat(pipeline.execute(), is(expected));
    }

    @Test
    public void singleWordContainingPunctuation() {
        final Stream<String> input = Stream.of("!!example!!");
        final Pipeline pipeline = new Pipeline(input);

        Map<Integer, Long> expected = new HashMap<>();
        expected.put(7, 1L);

        assertThat(pipeline.execute(), is(expected));
    }

    @Test
    public void singleWordContainingOnlyPunctuation() {
        final Stream<String> input = Stream.of("-..-");
        final Pipeline pipeline = new Pipeline(input);

        assertThat(pipeline.execute(), is(Collections.EMPTY_MAP));
    }

    @Test
    public void singleLineMultipleWords() {
        final Stream<String> input = Stream.of("This is an example sentence");
        final Pipeline pipeline = new Pipeline(input);

        Map<Integer, Long> expected = new HashMap<>();
        expected.put(8, 1L);
        expected.put(4, 1L);
        expected.put(7, 1L);
        expected.put(2, 2L);

        assertThat(pipeline.execute(), is(expected));
    }

    @Test
    public void singleLineMultipleWordsContainingPunctuation() {
        final Stream<String> input = Stream.of("This is; (another) example sentence!");
        final Pipeline pipeline = new Pipeline(input);

        Map<Integer, Long> expected = new HashMap<>();
        expected.put(8, 1L);
        expected.put(4, 1L);
        expected.put(7, 2L);
        expected.put(2, 1L);

        assertThat(pipeline.execute(), is(expected));
    }

    @Test
    public void singleLineMultipleWordsContainingOnlyPunctuation() {
        final Stream<String> input = Stream.of("- .... .. ... .. ... .- -. . -..- .- -- .--. .-.. . ... . -. - . -. -.-. .");
        final Pipeline pipeline = new Pipeline(input);

        assertThat(pipeline.execute(), is(Collections.EMPTY_MAP));
    }

    @Test
    public void multipleSingleWordLines() {
        final Stream<String> input = Stream.of("This", "is", "an", "example", "sentence", "over", "several", "lines");
        final Pipeline pipeline = new Pipeline(input);

        Map<Integer, Long> expected = new HashMap<>();
        expected.put(8, 1L);
        expected.put(4, 2L);
        expected.put(7, 2L);
        expected.put(2, 2L);
        expected.put(5, 1L);

        assertThat(pipeline.execute(), is(expected));
    }

    @Test
    public void multipleSingleWordLinesContainingPunctuation() {
        final Stream<String> input = Stream.of("!This:", "is-", "(an)", "example.", "?sentence,", "but\"", "!with;", "non-alpha", "chars!");
        final Pipeline pipeline = new Pipeline(input);

        Map<Integer, Long> expected = new HashMap<>();
        expected.put(8, 1L);
        expected.put(4, 2L);
        expected.put(5, 1L);
        expected.put(7, 1L);
        expected.put(2, 2L);
        expected.put(3, 1L);
        expected.put(9, 1L);

        assertThat(pipeline.execute(), is(expected));
    }

    @Test
    public void multipleSingleWordLinesContainingOnlyPunctuation() {
        final Stream<String> input = Stream.of(".", "-..-", ".-", "--", ".--.", ".-..", ".");
        final Pipeline pipeline = new Pipeline(input);

        assertThat(pipeline.execute(), is(Collections.EMPTY_MAP));
    }

    @Test
    public void multipleLinesMultipleWords() {
        final Stream<String> input = Stream.of(
                "Shall I compare thee to a summers day",
                "Thou art more lovely and more temperate",
                "Rough winds do shake the darling buds of May",
                "And summers lease hath all too short a date");
        final Pipeline pipeline = new Pipeline(input);

        Map<Integer, Long> expected = new HashMap<>();
        expected.put(1, 3L);
        expected.put(2, 3L);
        expected.put(3, 8L);
        expected.put(4, 7L);
        expected.put(5, 6L);
        expected.put(6, 1L);
        expected.put(7, 4L);
        expected.put(9, 1L);

        assertThat(pipeline.execute(), is(expected));
    }


    /* These results differ from the test above as mid-word punctuation is preserved
     * i.e. summers != summer's */
    @Test
    public void multipleLinesMultipleWordsContainingPunctuation(){
        final Stream<String> input = Stream.of(
                "Shall I compare thee to a summer’s day?",
                "Thou art more lovely and more temperate:",
                "Rough winds do shake the darling buds of May,",
                "And summer’s lease hath all too short a date;");
        final Pipeline pipeline = new Pipeline(input);

        Map<Integer, Long> expected = new HashMap<>();
        expected.put(1, 3L);
        expected.put(2, 3L);
        expected.put(3, 8L);
        expected.put(4, 7L);
        expected.put(5, 6L);
        expected.put(6, 1L);
        expected.put(7, 2L);
        expected.put(8, 2L); // summer's is longer than summers
        expected.put(9, 1L);

        assertThat(pipeline.execute(), is(expected));
    }

    @Test
    public void multipleLineMultipleWordsOnlyPunctuation() {
        final Stream<String> input = Stream.of(
                "- .... .. ...",
                ".. ...",
                ".- -.",
                ". -..- .- -- .--. .-.. .");
        final Pipeline pipeline = new Pipeline(input);

        assertThat(pipeline.execute(), is(Collections.EMPTY_MAP));
    }

    @Test
    public void specifiedExample() {
        final Stream<String> input = Stream.of("Hello world & good morning. The date is 18/05/2016");
        final Pipeline pipeline = new Pipeline(input);

        Map<Integer, Long> expected = new HashMap<>();
        expected.put(1, 1L);
        expected.put(2, 1L);
        expected.put(3, 1L);
        expected.put(4, 2L);
        expected.put(5, 2L);
        expected.put(7, 1L);
        expected.put(10, 1L);

        assertThat(pipeline.execute(), is(expected));
    }
}
