package com.jdarley.frequency.pipeline;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.EMPTY_LIST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class FunctionTest {

    @Test
    public void toWordStreamSplitsSentenceBySpaces() {
        final String input = "this is an example string";
        List<String> output = Functions.toWordStream.apply(input).collect(Collectors.toList());

        assertThat(output, contains("this", "is", "an", "example", "string"));
    }

    @Test
    public void toWordStreamHandlesEmptyStrings() {
        final String input = "";
        List<String> output = Functions.toWordStream.apply(input).collect(Collectors.toList());

        assertThat(output, equalTo(Collections.emptyList()));
    }

    @Test
    public void stripPunctuationRemovesLeadingPunctuation() {
        final String input = "(!.-:,*?)\"Before";
        final String output = Functions.stripPunctuation.apply(input);

        assertThat(output, equalTo("Before"));
    }

    @Test
    public void stripPunctuationRemovesTrailingPunctuation() {
        final String input = "After(!.-:,*?)\"";
        final String output = Functions.stripPunctuation.apply(input);

        assertThat(output, equalTo("After"));
    }

    @Test
    public void stripPunctuationRemovesSurroundingPunctuation() {
        final String input = "!!--**Around**--!!";
        final String output = Functions.stripPunctuation.apply(input);

        assertThat(output, equalTo("Around"));
    }

    @Test
    public void stripPunctuationPreservesPunctuationWithinWord() {
        final String input = "hyphenated-word";
        final String output = Functions.stripPunctuation.apply(input);

        assertThat(output, equalTo("hyphenated-word"));
    }

    @Test
    public void stripPunctuationPreservesSpaces() {
        final String input = "   Spaces   ";
        final String output = Functions.stripPunctuation.apply(input);

        assertThat(output, equalTo("   Spaces   "));
    }

    @Test
    /* This is an example from the example input */
    public void stripPunctuationPreservesAmpersands() {
        final String input = "&";
        final String output = Functions.stripPunctuation.apply(input);

        assertThat(output, equalTo("&"));
    }

    @Test
    /* This is a real example found in the example Bible text */
    public void stripPunctuationUsingRealExample() {
        final String input = "sin--;";
        final String output = Functions.stripPunctuation.apply(input);

        assertThat(output, equalTo("sin"));
    }

}
