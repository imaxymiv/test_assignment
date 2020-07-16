package com.assignment.www.aws;

import com.assignment.www.models.Snapshot;

import java.util.List;
import java.util.stream.Collectors;

import static com.assignment.www.utils.StringFormatter.getFormattedString;

public class AWSSnapshot extends AwsObj<Snapshot> {

    @Override
    public void print(String objToPrint) {
        System.out.println(getFormattedString(objToPrint));
    }

    public List<Snapshot> getSnapshots(String dataToParse) {
        return getFormattedString(dataToParse).stream()
                .map(map -> mapper.convertToPojo(map, Snapshot.class)).collect(Collectors.toList());
    }
}
