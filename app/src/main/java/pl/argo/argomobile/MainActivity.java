/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package pl.argo.argomobile;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.ros.android.MessageCallable;
import org.ros.android.RosActivity;
import org.ros.android.view.RosTextView;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;
import org.ros.rosjava_tutorial_pubsub.Talker;

import io.github.controlwear.virtual.joystick.android.JoystickView;

/**
 * @author damonkohler@google.com (Damon Kohler)
 */
public class MainActivity extends RosActivity {

    private RosTextView<std_msgs.String> rosTextView;

    public MainActivity() {
        // The RosActivity constructor configures the notification title and ticker
        // messages.
        //super("Pubsub Tutorial", "Pubsub Tutorial");
        super("sampleNotificationTicker", "sampleNotificationTitle");
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rosTextView = (RosTextView<std_msgs.String>) findViewById(R.id.text);
        rosTextView.setTopicName("hmm");
        rosTextView.setMessageType(std_msgs.String._TYPE);
        rosTextView.setMessageToStringCallable(new MessageCallable<String, std_msgs.String>() {
            @Override
            public String call(std_msgs.String message) {
                return message.getData();
            }
        });

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        JoystickView joystick = (JoystickView) findViewById(R.id.joystickView2);
        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                Log.d("xPos = ", String.valueOf(joystick.getNormalizedX()));
                //Log.d("xPos = ", String.valueOf(joystick.getX()));
                /*Log.d("xPos = ",  String.format("x%03d:y%03d",
                        joystick.getNormalizedX(),
                        joystick.getNormalizedY()));*/
                //Log.d("angle = ", angle + "°");
                //Log.d("strength = ", strength + "%");
                double x = Math.round(strength * Math.cos(angle * Math.PI / 180) * 100) / 100.00;
                double y = Math.round(strength * Math.sin(angle * Math.PI / 180) * 100) / 100.00;

                String angleStrengthText = "angle = " + angle + "°\n";
                angleStrengthText += "strength = " + strength + "%\n";
                angleStrengthText += "x = " + x + "\n";
                angleStrengthText += "y = " + y;


                ((TextView) findViewById(R.id.textView2)).setText(angleStrengthText);
            }
        });
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        Talker talker = new Talker("hmm");

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
}