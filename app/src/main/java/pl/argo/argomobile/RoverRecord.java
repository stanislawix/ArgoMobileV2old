package pl.argo.argomobile;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class RoverRecord {

    private int id;
    private int imageId;
    private String name;
    private String topicPrefix;

    private List<String> jointNames;//TODO: zmienić środek na JointState??
}


