package pl.argo.argomobile;

import android.content.Intent;
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

//import org.ros.android.view.RosTextView;
import org.jetbrains.annotations.NotNull;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends RosActivity {//AppCompatActivity

    public static final String TAG = "ArgoMobile";

    private ParametrizedTalker talker;
    //private RosTextView<std_msgs.String> rosTextView;//TextView na górze był wcześniej rodzaju RosTextView

    public double scale = 0.2;

    double x, y, z;

    //double[] manipsStates = new double[6];

    private int roverId;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settingsButton) {
            Intent intent = new Intent(this, RoverChooserActivity.class);
            activityResultLauncher.launch(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public MainActivity() {
        super("ArgoMobile", "ArgoMobile");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }


    //AppCompatActivity appCompatActivity = new AppCompatActivity();
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == 1) {
                        Intent intent = result.getData();
                        //System.out.println("received roverName = " + intent.getStringExtra("roverName"));
                        Log.d(TAG, "received roverId = " + intent.getIntExtra("roverId", -1));


                        RoverService roverService = RoverService.getInstance();

                        TextView roverName = findViewById(R.id.roverName);
                        roverId = intent.getIntExtra("roverId", -1);
                        roverName.setText(roverService.getRoverById(roverId).getName());
                    }
                }
            }
    );

    /*@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        //Log.d(TAG, "onCreate: savedInstanceState=" + savedInstanceState);

        if(savedInstanceState == null || savedInstanceState.getString("roverId") == null) {
            Intent intent = new Intent(this, RoverChooserActivity.class);
            activityResultLauncher.launch(intent);
        }
        else roverId = savedInstanceState.getInt("roverId");
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: savedInstanceState=" + savedInstanceState);

        if(savedInstanceState == null || savedInstanceState.getString("roverId") == null) {
            Intent intent = new Intent(this, RoverChooserActivity.class);
            activityResultLauncher.launch(intent);
        }
        else roverId = savedInstanceState.getInt("roverId");

        /*rosTextView = (RosTextView<std_msgs.String>) findViewById(R.id.text);
        rosTextView.setTopicName("hmm");
        rosTextView.setMessageType(std_msgs.String._TYPE);
        rosTextView.setMessageToStringCallable(new MessageCallable<String, std_msgs.String>() {
            @Override
            public String call(std_msgs.String message) {
                return message.getData();
            }
        });*/

        //TextView textView2 = (TextView) findViewById(R.id.textView2);
        JoystickView cmdVelJoystick = (JoystickView) findViewById(R.id.cmd_vel_joystick);

        cmdVelJoystick.setOnMoveListener((angle, strength) -> {
            ((TextView) findViewById(R.id.cmd_vel_joystick_info)).setText(countJoystickSwingValues(angle, strength));

            if(!((Switch) findViewById(R.id.isAngular)).isChecked()) z = 0;
            else {
                z = -x;
                x = 0;
            }

            talker.getLinear().setX(x);
            talker.getLinear().setY(y);
            talker.getAngular().setZ(z);
        });

        SeekBar manip1 = (SeekBar) findViewById(R.id.manip_1_seekbar);
        SeekBar manip2 = (SeekBar) findViewById(R.id.manip_2_seekbar);
        SeekBar manip3 = (SeekBar) findViewById(R.id.manip_3_seekbar);
        SeekBar manip4 = (SeekBar) findViewById(R.id.manip_4_seekbar);
        SeekBar manip5 = (SeekBar) findViewById(R.id.manip_5_seekbar);
        SeekBar manip6 = (SeekBar) findViewById(R.id.manip_6_seekbar);

        manip1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {//uprościć do jednej klasy
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                talker.manipsStates[0] = seekBar.getProgress()-100;
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

        manip2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                talker.manipsStates[1] = seekBar.getProgress()-100;
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

        manip3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                talker.manipsStates[2] = seekBar.getProgress()-100;
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

        manip4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                talker.manipsStates[3] = seekBar.getProgress()-100;
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

        manip5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                talker.manipsStates[4] = seekBar.getProgress()-100;
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

        manip6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                talker.manipsStates[5] = seekBar.getProgress()-100;
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

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roverId", roverId);
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
        //nodeMainExecutor.execute(rosTextView, nodeConfiguration);
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

    public void onSeekBarClicked(View view) {
        Toast.makeText(getBaseContext(), "same thing, but in the method", Toast.LENGTH_SHORT).show();
    }

    /*public void onSeekBarChange(View view) {
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
    }*/
}