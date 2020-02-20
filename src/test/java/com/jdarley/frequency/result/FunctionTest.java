package com.jdarley.frequency.result;

import org.junit.jupiter.api.RepeatedTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class FunctionTest {

    @RepeatedTest(10)
    public void randomProduct() {
        Random r = new Random();

        final Integer freq = r.nextInt();
        final Long count = r.nextLong();

        final Map<Integer, Long> input = new HashMap<>();
        input.put(freq, count);

        final List<Long> output = input.entrySet().stream().map(Functions.product).collect(Collectors.toList());

        assertThat(output, contains(freq * count));
    }
}
