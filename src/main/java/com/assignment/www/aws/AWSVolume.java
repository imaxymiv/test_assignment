package com.assignment.www.aws;

import com.assignment.www.models.Volume;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.assignment.www.utils.StringFormatter.getFormattedString;

public class AWSVolume extends AwsObj<Volume> {

    @Override
    public void print(String objToPrint) {
        System.out.println(getFormattedString(objToPrint));
    }

    public List<Volume> getVolumes(String dataToParse) {
        return getFormattedString(dataToParse).stream()
                .map(map -> mapper.convertToPojo(map, Volume.class)).collect(Collectors.toList());
    }

    public List<Volume> findVolumesAttachedToInstances(String dataToParse) {
        Predicate<Volume> filter = volume -> volume.getAttachedInstanceId() > 0;
        return lookup(getVolumes(dataToParse), filter);
    }
}
