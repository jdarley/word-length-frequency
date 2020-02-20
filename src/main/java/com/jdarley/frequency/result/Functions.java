package com.jdarley.frequency.result;

import java.util.Map;
import java.util.function.Function;

public class Functions {

    public static final Function<Map.Entry<Integer, Long>, Long> product = (e) -> e.getKey() * e.getValue();

}
