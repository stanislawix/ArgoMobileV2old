package pl.argo.argomobile.data.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RoverDto {
    public int id;

    public String name;

    public String topicPrefix;

    public List<String> jointNames;
}
