package pl.argo.argomobile.data.dto;

import pl.argo.argomobile.data.Joint;
import pl.argo.argomobile.data.Rover;

import java.util.List;
import java.util.stream.Collectors;

public class RoverMapper {
    public RoverDto toDto(Rover rover, List<Joint> roverJoints) {
        return RoverDto.builder()
                .id(rover.getId())
                .name(rover.getName())
                .topicPrefix(rover.getTopicPrefix())
                .jointNames(roverJoints.stream().map(Joint::getJointName).collect(Collectors.toList()))
                .build();
    }

}
