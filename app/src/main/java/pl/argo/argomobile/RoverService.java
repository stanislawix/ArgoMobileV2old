package pl.argo.argomobile;

import java.util.ArrayList;
import java.util.List;

public class RoverService {

    private static RoverService instance;

    private List<RoverRecord> roverRecordsList;

    private RoverService() {
        roverRecordsList = new ArrayList<>();

        roverRecordsList.add(new RoverRecord(1, R.drawable.argo_v1, "Argo V1"));
        roverRecordsList.add(new RoverRecord(2, R.drawable.argo_v2, "Argo V2"));
        roverRecordsList.add(new RoverRecord(3, R.drawable.argo_v3, "Argo V3"));

        roverRecordsList.add(new RoverRecord(4, R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(5, R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(6, R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(7, R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(8, R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(9, R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(10, R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(11, R.drawable.argo_v3, "Argo V3"));
    }

    public static RoverService getInstance() {
        if (instance == null) instance = new RoverService();
        return instance;
    }

    public RoverRecord getRoverById(int id) {
        return roverRecordsList
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<RoverRecord> getAllRovers() {
        return roverRecordsList;
    }
}
