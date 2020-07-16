package com.assignment.www.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@JsonNaming(SnakeCaseStrategy.class)
@Data
public class Volume {
    int id;
    String name;
    String state;
    String region;
    int attachedInstanceId;
}
