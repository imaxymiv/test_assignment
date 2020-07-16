package com.assignment.www.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@JsonNaming(SnakeCaseStrategy.class)
@Data
public class Snapshot {
    private int id;
    private String name;
    private String region;
    private int sourceVolumeID;
}
