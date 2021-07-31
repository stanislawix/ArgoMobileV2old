package pl.argo.argomobile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import org.ros.android.RosActivity;
//import org.ros.android.view.RosTextView;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.util.Collections;

import io.github.controlwear.virtual.joystick.android.JoystickView;


public class MainActivity extends RosActivity {

    private ParametrizedTalker talker;
    //private RosTextView<std_msgs.String> rosTextView;

    public double scale = 0.2;

    double x, y, z;

    public MainActivity() {
        // The RosActivity constructor configures the notification title and ticker
        // messages.
        //super("Pubsub Tutorial", "Pubsub Tutorial");
        super("sampleNotificationTicker", "sampleNotificationTitle");
    }


    //@SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        cmdVelJoystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                ((TextView) findViewById(R.id.cmd_vel_joystick_info)).setText(countJoystickSwingValues(angle, strength));

                if(!((Switch) findViewById(R.id.isAngular)).isChecked()) z = 0;
                else {
                    z = -x;
                    x = 0;
                }

                talker.getLinear().setX(x);
                talker.getLinear().setY(y);
                talker.getAngular().setZ(z);
            }
        });

        JoystickView manip1Joystick = (JoystickView) findViewById(R.id.manip_1_joystick);

        manip1Joystick.setOnMoveListener(new JoystickView.OnMoveListener() {

            @Override
            public void onMove(int angle, int strength) {
                ((TextView) findViewById(R.id.cmd_vel_joystick_info)).setText(countJoystickSwingValues(angle, strength));

                if(((Switch) findViewById(R.id.isAngular)).isChecked()) z = 0;
                else {
                    z = x;
                    x = 0;
                }

                //TODO: zmienić na faktyczny ruch joysticka
                talker.getManip1().setPosition(new double[]{0.05});

                //talker.getLinear().setX(x);
                //talker.getLinear().setY(y);
                //talker.getAngular().setZ(z);
            }
        });
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