package pl.argo.argomobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import org.ros.android.view.RosTextView;
import org.ros.android.MessageCallable;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.lang.reflect.Field;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends RosActivity {//AppCompatActivity

    public static final String TAG = "ArgoMobile";

    private ParametrizedTalker talker;
    private RosTextView<std_msgs.String> rosTextView;

    private SharedPreferences mPrefs;
    private RoverService roverService = RoverService.getInstance();
    private int roverId;

    private double scale = 0.2;
    private double x, y, z;

    public MainActivity() {
        super("ArgoMobile", "ArgoMobile");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == 1) {
                        Intent intent = result.getData();
                        roverId = intent.getIntExtra("roverId", -1);
                        updateActivityContentAndRoverObject();
                    }
                }
            }
    );

    @SuppressLint("DefaultLocale")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Odczyt z pamięci trwałej.
        mPrefs = getSharedPreferences(getLocalClassName(), MODE_PRIVATE);
        roverId = mPrefs.getInt("roverId", 1);

        updateActivityContentAndRoverObject();

        rosTextView = findViewById(R.id.rosTextView);//(RosTextView<std_msgs.String>)
        rosTextView.setTopicName(roverService.getRoverById(roverId).getTopicPrefix());//czy tyle wystarczy??
        rosTextView.setMessageType(std_msgs.String._TYPE);
        rosTextView.setMessageToStringCallable(new MessageCallable<String, std_msgs.String>() {
            @Override
            public String call(std_msgs.String message) {
                return message.getData();
            }
        });

        JoystickView cmdVelJoystick = (JoystickView) findViewById(R.id.cmd_vel_joystick);

        cmdVelJoystick.setOnMoveListener((angle, strength) -> {
            ((TextView) findViewById(R.id.cmd_vel_joystick_info)).setText(countJoystickSwingValues(angle, strength));

            if(!((Switch) findViewById(R.id.isAngular)).isChecked()) z = 0;
            else {
                z = -x;
                x = 0;
            }

            try {
                talker.getLinear().setX(x);
                talker.getLinear().setY(y);
                talker.getAngular().setZ(z);
            }
            catch (NullPointerException e) {
                Toast.makeText(MainActivity.this, R.string.unable_to_communicate_with_server, Toast.LENGTH_SHORT).show();
            }
        });

        SeekBar[] manips = new SeekBar[6];
        for(int i=0; i<6; ++i) {
            manips[i] = (SeekBar) findViewById(getResId(String.format("manip_%d_seekbar", (i+1)), R.id.class));

            int iCopy = i;
            manips[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    try {
                        talker.manipsStates[iCopy] = seekBar.getProgress()-100;
                    }
                    catch (NullPointerException e) {
                        Toast.makeText(MainActivity.this, R.string.unable_to_communicate_with_server, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    //nic
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    seekBar.setProgress(100);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Zapis do pamięci trwałej.
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt("roverId", roverId);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settingsButton) {
            Intent intent = new Intent(this, RoverChooserActivity.class);
            activityResultLauncher.launch(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Metoda aktualizująca aktualne activity oraz obiekt samego łazika - w zależności od wybranego łazika (różne joysticki dla różnych łazików)
     */
    @SuppressLint("SetTextI18n")
    private void updateActivityContentAndRoverObject() {
        RoverRecord roverRecord = roverService.getRoverById(roverId);
        TextView roverName = findViewById(R.id.roverName);

        roverName.setText(getString(R.string.current_rover) + roverRecord.getName());

        if(talker != null) {
            talker.initializeManipsStates(roverRecord.getJointNames().size());
            talker.setRoverRecord(roverRecord);
        }

        for(int i=0; i<6; ++i) {
            SeekBar seekBar = findViewById(getResId(String.format("manip_%d_seekbar", (i + 1)), R.id.class));
            TextView textView = findViewById(getResId(String.format("manip_%d_info", (i + 1)), R.id.class));

            if(roverRecord.getJointNames() != null && i < roverRecord.getJointNames().size()) {
                seekBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView.setText(roverRecord.getJointNames().get(i));
            }
            else {
                seekBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        talker = new ParametrizedTalker();

        // At this point, the user has already been prompted to either enter the URI
        // of a master to use or to start a master locally.

        // The user can easily use the selected ROS Hostname in the master chooser
        // activity.
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname());
        nodeConfiguration.setMasterUri(getMasterUri());
        nodeMainExecutor.execute(talker, nodeConfiguration);


        // The RosTextView is also a NodeMain that must be executed in order to
        // start displaying incoming messages.
        nodeMainExecutor.execute(rosTextView, nodeConfiguration);
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_0_2:
                if (checked)
                    scale = 0.2;
                break;
            case R.id.radio_0_5:
                if (checked)
                    scale = 0.5;
                break;
            case R.id.radio_1:
                if (checked)
                    scale = 1;
                break;
            case R.id.radio_3:
                if (checked)
                    scale = 3;
                break;
            case R.id.radio_6:
                if (checked)
                    scale = 6;
                break;
        }
        Log.d("scale = ", String.valueOf(scale));
    }

    private String countJoystickSwingValues(int angle, int strength) {
        x = Math.round(strength * Math.cos(angle * Math.PI / 180) * 100 * scale / 100.00 ) / 100.00;
        y = Math.round(strength * Math.sin(angle * Math.PI / 180) * 100 * scale / 100.00) / 100.00;

        String out = "";
        out += "angle = " + angle + "°\n";
        out += "strength = " + strength + "%\n";
        out += "x = " + x + "\n";
        out += "y = " + y + "\n";

        return out;
    }
}