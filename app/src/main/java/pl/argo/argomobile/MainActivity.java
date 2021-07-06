package pl.argo.argomobile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.ros.android.RosActivity;
import org.ros.android.view.RosTextView;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import io.github.controlwear.virtual.joystick.android.JoystickView;


public class MainActivity extends RosActivity {

    private ParametrizedTalker talker;
    private RosTextView<std_msgs.String> rosTextView;

    public int scale = 1;

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
        JoystickView joystickLinear = (JoystickView) findViewById(R.id.joystickLinear);
        JoystickView joystickAngular = (JoystickView) findViewById(R.id.joystickAngular);

        RadioGroup scaleGroup = (RadioGroup) findViewById(R.id.scaleGroup);

        //Button

        /*final int[] scale = {0};

        scaleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("checkedId = ", String.valueOf(checkedId));
            }

            public void onRadioButtonClicked(View view) {
                // Is the button now checked?
                boolean checked = ((RadioButton) view).isChecked();

                // Check which radio button was clicked
                switch(view.getId()) {
                    case R.id.radio_1:
                        if (checked)
                            scale[0] = 1;
                            break;
                    case R.id.radio_20:
                        if (checked)
                            scale[0] = 20;
                            break;
                    case R.id.radio_100:
                        if (checked)
                            scale[0] = 100;
                        break;
                }
                Log.d("scale = ", String.valueOf(scale[0]));
            }

        });*/

        //boolean checked = ((RadioButton) view

        joystickLinear.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                double x = Math.round(strength * Math.cos(angle * Math.PI / 180) * 100) / 100.00 * scale / 100.00;
                double y = Math.round(strength * Math.sin(angle * Math.PI / 180) * 100) / 100.00 * scale / 100.00;
                double z = 0;

                String angleStrengthText = "Linear Joystick:\n";
                angleStrengthText += "angle = " + angle + "°\n";
                angleStrengthText += "strength = " + strength + "%\n";
                angleStrengthText += "x = " + x + "\n";
                angleStrengthText += "y = " + y + "\n";
                angleStrengthText += "z = " + z;


                ((TextView) findViewById(R.id.LinearJoystickInfo)).setText(angleStrengthText);

                //talker.setAngular(((Switch) findViewById(R.id.isAngular)).isChecked());

                talker.getLinear().setX(x);
                talker.getLinear().setY(y);
                talker.getLinear().setZ(z);
            }
        });

        joystickAngular.setOnMoveListener(new JoystickView.OnMoveListener() {

            @Override
            public void onMove(int angle, int strength) {
                double x = 0;
                double y = 0;
                double z = - Math.round(strength * Math.cos(angle * Math.PI / 180) * 100) / 100.00 * scale / 100.00;

                String angleStrengthText = "Angular Joystick:\n";
                angleStrengthText += "angle = " + angle + "°\n";
                angleStrengthText += "strength = " + strength + "%\n";
                angleStrengthText += "x = " + x + "\n";
                angleStrengthText += "y = " + y + "\n";
                angleStrengthText += "z = " + z;

                ((TextView) findViewById(R.id.AngularJoystickInfo)).setText(angleStrengthText);

                talker.getAngular().setX(x);
                talker.getAngular().setY(y);
                talker.getAngular().setZ(z);
            }
        });
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        talker = new ParametrizedTalker();
        //ParametrizedTalkerV2 talker = new ParametrizedTalkerV2();

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
            case R.id.radio_1:
                if (checked)
                    scale = 1;
                break;
            case R.id.radio_20:
                if (checked)
                    scale = 20;
                break;
            case R.id.radio_100:
                if (checked)
                    scale = 100;
                break;
        }
        Log.d("scale = ", String.valueOf(scale));
    }
}