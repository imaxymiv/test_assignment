package com.assignment.www.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Mapper {

    private final ObjectMapper objectMapper;

    public Mapper() {
        this.objectMapper = new ObjectMapper();
    }


    /**
     * @param objectToConvert map representation of object to map to POJO
     * @param classToMap model to map object to
     * @param <T> type of objects to return
     * @return mapped to Class<T> object
     */
    public <T> T convertToPojo(Map<String, String> objectToConvert, Class<T> classToMap) {
        return objectMapper.convertValue(objectToConvert, classToMap);
    }
}
