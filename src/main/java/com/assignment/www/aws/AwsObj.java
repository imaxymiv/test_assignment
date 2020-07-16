package com.assignment.www.aws;

import com.assignment.www.mapper.Mapper;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AwsObj<T> {

    protected final Mapper mapper;

    protected AwsObj() {
        mapper = new Mapper();
    }

    public abstract void print(String objToPrint);

    /**
     * @param listOfObjects list of objects to filter
     * @param predicate filter
     * @return list of filtered objects
     */
    public List<T> lookup(List<T> listOfObjects, Predicate<T> predicate) {
        return listOfObjects.stream().filter(predicate).collect(Collectors.toList());
    }
}
