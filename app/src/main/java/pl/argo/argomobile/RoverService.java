package pl.argo.argomobile;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoverService {

    private static RoverService instance;

    private List<RoverRecord> roverRecordsList;

    private RoverService() {
        roverRecordsList = new ArrayList<>();

        roverRecordsList.add(new RoverRecord(1, R.drawable.argo_v3, "Argo V3", "argo", Arrays.asList("manip_1", "manip_2", "manip_3", "manip_4", "manip_5", "manip_6")));//ta wersja jest chyba najlepsza
        roverRecordsList.add(new RoverRecord(2, R.drawable.dzik, "Dzik", "argo_mini", null));
        roverRecordsList.add(new RoverRecord(3, R.drawable.hyperion, "Hyperion", "hyperion", Arrays.asList("manip_1", "manip_2", "manip_3", "manip_4")));
        roverRecordsList.add(new RoverRecord(4, R.drawable.next, "Next", "next", Arrays.asList("joint1", "joint2", "joint3", "joint4")));
        roverRecordsList.add(new RoverRecord(5, R.drawable.red, "Red", "red", Arrays.asList("manip1", "manip2", "manip3", "manip4")));
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
