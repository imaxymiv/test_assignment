package com.assignment.www.objectmappertests;

import com.assignment.www.aws.AWSInstance;
import com.assignment.www.aws.AWSSnapshot;
import com.assignment.www.aws.AWSVolume;
import com.assignment.www.models.Instance;
import com.assignment.www.models.Snapshot;
import com.assignment.www.models.Volume;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.function.Predicate;

public class ObjectMapperTests {

    private AWSInstance awsInstance;
    private AWSVolume awsVolume;
    private AWSSnapshot awsSnapshot;

    private static final String AWS_INSTANCE_DATA = "id:1100,type:micro,state:running,region:oregon" +
            "%id:1200,type:large1,state:terminated,region:n.virginia%id:1300,type:xlarge3,state:stopped," +
            "region:pasific%id:1400,type:large1,state:running,region:oregon";

    private static final String AWS_VOLUME_DATA = "id:2100,name:data1,state:available,region:ohio,attached_instance_id:" +
            "%id:2200,name:data1,state:in-use,region:ohio,attached_instance_id:1100%" +
            "id:2300,name:data2,state:available,region:london,attached_instance_id:1200%" +
            "id:2400,name:data2,state:in-use,region:oregon,attached_instance_id:1300";

    private static final String AWS_SNAPSHOT_DATA = "id:3100,name:data1_backup,region:oregon,source_volume_id:2100%" +
            "id:3200,name:data2_backup,region:virginia,source_volume_id:2400%";


    @BeforeClass
    public void setUp() {
        awsInstance = new AWSInstance();
        awsVolume = new AWSVolume();
        awsSnapshot = new AWSSnapshot();
    }

    @Test
    public void test_instance_mapping() {
        var listOfInstances = awsInstance.getInstances(AWS_INSTANCE_DATA);

        Assertions.assertThat(listOfInstances.size()).isPositive();
        Assertions.assertThat(listOfInstances.stream()).extracting(Instance::getType)
                .contains("micro", "large1", "xlarge3");
    }

    @Test
    public void test_volume_mapping() {
        var listOfVolumes = awsVolume.getVolumes(AWS_VOLUME_DATA);

        Assertions.assertThat(listOfVolumes.size()).isPositive();
        Assertions.assertThat(listOfVolumes.stream()).extracting(Volume::getAttachedInstanceId)
                .contains(1200, 1300);
    }

    @Test
    public void test_snapshot_mapping() {
        var listOfSnapshot = awsSnapshot.getSnapshots(AWS_SNAPSHOT_DATA);

        Assertions.assertThat(listOfSnapshot.size()).isPositive();
        Assertions.assertThat(listOfSnapshot.stream()).extracting(Snapshot::getRegion)
                .contains("oregon", "virginia");
    }

    @Test
    public void test_available_volumes_search() {
        var awsVolumeVolumes = awsVolume.getVolumes(AWS_VOLUME_DATA);

        Predicate<Volume> filter = volume -> volume.getState().equalsIgnoreCase("available");
        var res = awsVolume.lookup(awsVolumeVolumes, filter);

        Assertions.assertThat(res).extracting(Volume::getState).containsOnly("available");
    }

    @Test
    public void test_volume_search_in_region() {
        var awsVolumeVolumes = awsVolume.getVolumes(AWS_VOLUME_DATA);

        Predicate<Volume> filter = volume -> volume.getState()
                .equalsIgnoreCase("available") && volume.getRegion().equalsIgnoreCase("ohio");
        var res = awsVolume.lookup(awsVolumeVolumes, filter);

        Assertions.assertThat(res).extracting(Volume::getState, Volume::getRegion)
                .containsOnly(Tuple.tuple("available", "ohio"));
    }

    @Test
    public void test_running_instances_in_region() {
        var awsInstanceInstances = awsInstance.getInstances(AWS_INSTANCE_DATA);

        Predicate<Instance> filter = volume -> volume.getState()
                .equalsIgnoreCase("running") && volume.getRegion().equalsIgnoreCase("oregon");

        var res = awsInstance.lookup(awsInstanceInstances, filter);

        Assertions.assertThat(res).extracting(Instance::getState, Instance::getRegion)
                .containsOnly(Tuple.tuple("running", "oregon"));
    }

    @Test
    public void test_attached_volumes_search() {
        var searchResult = awsVolume.findVolumesAttachedToInstances(AWS_VOLUME_DATA);

        Assertions.assertThat(searchResult).extracting(Volume::getAttachedInstanceId).containsOnly(1100, 1200, 1300);
    }

    @Test
    public void test_not_terminated_instances() {
        var searchResult = awsInstance.findNotTerminatedInstances(AWS_INSTANCE_DATA);

        Assertions.assertThat(searchResult).extracting(Instance::getState).doesNotContain("terminated");
    }
}
