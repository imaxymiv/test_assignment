package com.assignment.www.aws;

import com.assignment.www.models.Instance;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.assignment.www.utils.StringFormatter.getFormattedString;

public class AWSInstance extends AwsObj<Instance> {

    @Override
    public void print(String objToPrint) {
        System.out.println(getFormattedString(objToPrint));
    }

    public List<Instance> getInstances(String dataToParse) {
        return getFormattedString(dataToParse).stream()
                .map(map -> mapper.convertToPojo(map, Instance.class)).collect(Collectors.toList());
    }

    public List<Instance> findNotTerminatedInstances(String dataToParse) {
        Predicate<Instance> filter = volume -> !volume.getState().equalsIgnoreCase("terminated");
        return lookup(getInstances(dataToParse), filter);
    }
}
