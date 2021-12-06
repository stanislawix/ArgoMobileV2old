package pl.argo.argomobile;

import android.util.Log;

import org.ros.concurrent.CancellableLoop;
import org.ros.message.Time;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;  // This library give us the AbstractNodeMain interface (see ahead)
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;  // Import the publisher

import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import geometry_msgs.Twist;
//import sensor_msgs.JointState;

import lombok.Data;
import sensor_msgs.JointState;


/**
 * A simple {@link Publisher} {@link NodeMain}.
 */

@Data
public class ParametrizedTalker extends AbstractNodeMain { // Java nodes NEEDS to implement AbstractNodeMain

    //Czas pomiędzy kolejnymi wywołaniami wysyłania wiadomości wyrażony w milisekundach
    private static int sleepTime = 100;

    int seq = 0;

    boolean isAngular = false;

    Vector3Implemenation linear = new Vector3Implemenation();
    Vector3Implemenation angular = new Vector3Implemenation();

    RoverRecord roverRecord;

    Publisher<geometry_msgs.Twist> twistPublisher;
    Publisher<sensor_msgs.JointState> jointStatePublisher;


    //http://docs.ros.org/en/melodic/api/geometry_msgs/html/msg/Twist.html
    private Twist twist;

    //http://docs.ros.org/en/melodic/api/sensor_msgs/html/msg/JointState.html
    private JointState manips;

    //wartości effortu od -100 do 100
    double[] manipsStates = new double[6];

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava/talker");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        linear = new Vector3Implemenation();
        angular = new Vector3Implemenation();

        // This CancellableLoop will be canceled automatically when the node shuts
        // down.
        connectedNode.executeCancellableLoop(new CancellableLoop() {

            @Override
            protected void setup() {
            }

            @Override
            protected void loop() throws InterruptedException {
                if(roverRecord != null) {
                    twistPublisher = connectedNode.newPublisher("/" + roverRecord.getTopicPrefix() + "/cmd_vel", "geometry_msgs/Twist");
                    jointStatePublisher = connectedNode.newPublisher("/" + roverRecord.getTopicPrefix() + "/joint_states", sensor_msgs.JointState._TYPE);

                    long startTime = Calendar.getInstance().getTimeInMillis();

                    twist = twistPublisher.newMessage(); // Init a msg variable that of the publisher type

                    twist.getLinear().setX(linear.getY());//zamiana X i Y (tak mają roboty)
                    twist.getLinear().setY(-linear.getX());
                    twist.getLinear().setZ(linear.getZ());

                    twist.getAngular().setX(angular.getX());
                    twist.getAngular().setY(angular.getY());
                    twist.getAngular().setZ(angular.getZ());


                    manips = jointStatePublisher.newMessage();

                    manips.getHeader().setSeq(++seq);
                    manips.getHeader().setStamp(Time.fromMillis(System.currentTimeMillis()));//dodawanie dodatkowo frame_id jest niepotrzebne

                    manips.setName(roverRecord.getJointNames());
                    //manips.setPosition(manipsStates);
                    //manips.setVelocity(manipsStates);
                    manips.setEffort(manipsStates);

                    twistPublisher.publish(twist);
                    jointStatePublisher.publish(manips);


                    long elapsedTime = Calendar.getInstance().getTimeInMillis() - startTime;

                    if (elapsedTime < sleepTime) {
                        Thread.sleep(sleepTime - elapsedTime);
                    }
                    else {
                        Log.e(MainActivity.TAG, "loop: Wysyłanie wiadomości trwało więcej niż domyślny czas na to przeznaczony (" + elapsedTime +" ms).");
                    }
                }
            }
        });
    }

    public void initializeManipsStates(int size) {
        manipsStates = new double[size];
    }

}
