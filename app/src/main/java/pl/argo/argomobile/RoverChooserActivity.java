package pl.argo.argomobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import android.widget.Toast;
import androidx.annotation.Nullable;
import pl.argo.argomobile.data.dto.RoverDto;

/**
 * A class responsible for handling rover selection.
 */
public class RoverChooserActivity extends Activity {

    private ListView listView;

    private RoverService roverService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_chooser);
        setTitle(R.string.rover_chooser_title);

        listView = findViewById(R.id.roversList);

        roverService = RoverService.getInstance(getApplicationContext());

        updateListView();
    }

    private void updateListView() {
        RoverRecordAdapter roverRecordAdapter = new RoverRecordAdapter(this, R.layout.rover_list_row, roverService.getAllRovers());

        listView.setAdapter(roverRecordAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            RoverDto rover = (RoverDto) listView.getItemAtPosition(position);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("roverId", rover.getId());
            setResult(1, returnIntent);
            finish();
        });
    }

    public void updateRoversList(View view) {
        if(roverService.updateRoversFromApi()) {
            Toast.makeText(RoverChooserActivity.this, R.string.successfully_updated_rovers_database, Toast.LENGTH_SHORT).show();
            updateListView();
        } else {
            Toast.makeText(RoverChooserActivity.this, R.string.unable_to_update_rovers_database, Toast.LENGTH_SHORT).show();
        }

    }
}
