package pl.argo.argomobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static pl.argo.argomobile.MainActivity.TAG;

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

        RoverService roverService = RoverService.getInstance();

        RoverRecordAdapter roverRecordAdapter = new RoverRecordAdapter(this, R.layout.rover_list_row, roverService.getAllRovers());

        listView.setAdapter(roverRecordAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            RoverRecord roverRecord = (RoverRecord) listView.getItemAtPosition(position);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("roverId", roverRecord.getId());
            setResult(1, returnIntent);
            finish();
        });
    }
}
