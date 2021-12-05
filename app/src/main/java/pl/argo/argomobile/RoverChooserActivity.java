package pl.argo.argomobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static pl.argo.argomobile.MainActivity.ArgoMobile_TAG;

/**
 * A class responsible for handling rover selection.
 */
public class RoverChooserActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_chooser);
        setTitle(R.string.roverChooserTitle);

        listView = findViewById(R.id.roversList);

        List<RoverRecord> roverRecordsList = new ArrayList<>();

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

        RoverRecordAdapter roverRecordAdapter = new RoverRecordAdapter(this, R.layout.rover_list_row, roverRecordsList);

        listView.setAdapter(roverRecordAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            RoverRecord roverRecord = (RoverRecord) listView.getItemAtPosition(position);
            Toast.makeText(RoverChooserActivity.this, "position = " + position, Toast.LENGTH_SHORT).show();
            Log.d(ArgoMobile_TAG, "roverRecord = " + roverRecord.toString());

            Intent returnIntent = new Intent();
            returnIntent.putExtra("roverId", roverRecord.getId());
            returnIntent.putExtra("roverName", roverRecord.getName());
            setResult(1, returnIntent);
            //setResult(3,returnIntent);
            finish();
        });
    }
}
