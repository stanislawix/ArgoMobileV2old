package pl.argo.argomobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A class responsible for handling rover selection.
 */
public class RoverChooserActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_chooser);

        listView = findViewById(R.id.roversList);

        List<RoverRecord> roverRecordsList = new ArrayList<>();

        roverRecordsList.add(new RoverRecord(R.drawable.argo_v1, "Argo V1"));
        roverRecordsList.add(new RoverRecord(R.drawable.argo_v2, "Argo V2"));
        roverRecordsList.add(new RoverRecord(R.drawable.argo_v3, "Argo V3"));

        roverRecordsList.add(new RoverRecord(R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(R.drawable.argo_v3, "Argo V3"));
        roverRecordsList.add(new RoverRecord(R.drawable.argo_v3, "Argo V3"));

        RoverRecordAdapter roverRecordAdapter = new RoverRecordAdapter(this, R.layout.rover_list_row, roverRecordsList);

        listView.setAdapter(roverRecordAdapter);
    }
}
