package com.assignment.www.utils;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class StringFormatter {

    /**
     * @param stringToParse raw string po parse
     * @return List of maps where each "%" separated object is a separate map inside which each ":"
     * separated object is mapped to key/value (key is object before ":" and value is object after ":")
     *
     * if value is empty it's replaced on empty string. In terms of int it'll be 0;
     */
    public static List<Map<String, String>> getFormattedString(String stringToParse) {
        return Stream.of(stringToParse.split("%")).collect(Collectors.toList())
                .stream().map(s -> s.split(","))
                .map(splitString -> Arrays.stream(splitString)
                        .map(spl -> spl.split(":"))
                        .collect(Collectors.toMap(obj -> obj[0], obj -> {
                            var val = "";
                            if (obj.length > 1) {
                                val = obj[1];
                            }
                            return val;
                        }, (a, b) -> b)))
                .collect(Collectors.toList());
    }
}
