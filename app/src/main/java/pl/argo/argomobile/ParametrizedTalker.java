package pl.argo.argomobile;

import org.ros.concurrent.CancellableLoop;
import org.ros.message.Time;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;  // This library give us the AbstractNodeMain interface (see ahead)
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;  // Import the publisher

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import geometry_msgs.Twist;
//import sensor_msgs.JointState;

import lombok.Data;
import sensor_msgs.JointState;


/**
 * A simple {@link Publisher} {@link NodeMain}.
 */

@Data
public class ParametrizedTalker extends AbstractNodeMain { // Java nodes NEEDS to implement AbstractNodeMain
    int seq = 0;

    boolean isAngular = false;

    Vector3Implemenation linear = new Vector3Implemenation();
    Vector3Implemenation angular = new Vector3Implemenation();


    private Twist twist;
    private JointState manip1;

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava/talker");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        linear = new Vector3Implemenation();
        angular = new Vector3Implemenation();
        //final Publisher<geometry_msgs.Twist> twistPublisher = connectedNode.newPublisher("/turtle1/cmd_vel", geometry_msgs.Twist._TYPE); // That's how you create a publisher in Java!
        final Publisher<geometry_msgs.Twist> twistPublisher = connectedNode.newPublisher("/turtle1/cmd_vel", "geometry_msgs/Twist"); // That's how you create a publisher in Java!

        final Publisher<sensor_msgs.JointState> jointStatePublisher = connectedNode.newPublisher("/joint_states", sensor_msgs.JointState._TYPE);

        // This CancellableLoop will be canceled automatically when the node shuts
        // down.
        connectedNode.executeCancellableLoop(new CancellableLoop() {

            @Override
            protected void setup() {
            }

            @Override
            protected void loop() throws InterruptedException {
                twist = twistPublisher.newMessage(); // Init a msg variable that of the publisher type

                twist.getLinear().setX(linear.getX());
                twist.getLinear().setY(linear.getY());
                twist.getLinear().setZ(linear.getZ());

                twist.getAngular().setX(angular.getX());
                twist.getAngular().setY(angular.getY());
                twist.getAngular().setZ(angular.getZ());

                if(linear.getX() != 0 || linear.getY() != 0 || linear.getZ() != 0
                || angular.getX() != 0 || angular.getY() != 0 || angular.getZ() != 0) {
                    twistPublisher.publish(twist);       // Publish the message (if running use rostopic list to see the message)
                }

                manip1 = jointStatePublisher.newMessage();

                manip1.getHeader().setSeq(++seq);
                manip1.getHeader().setStamp(Time.fromMillis(System.currentTimeMillis()));
                manip1.setName(Collections.singletonList("manip_1"));
                //manip1.setName(Collections.singletonList("L_finger_jnt"));
                //manip1.setName(Collections.singletonList("manip_3"));
                double tmp = seq%2 == 0 ? -0.05 : 0.05;
                //manip1.setPosition(new double[]{0.05});//TODO: zmienić
                manip1.setPosition(new double[]{tmp});//TODO: zmienić

                //manip1.setEffort(new double[]{1});


                //TODO: wysyłanie tylko niezerowych wartości
                jointStatePublisher.publish(manip1);

                //Thread.sleep(10);             // Sleep for 1000 ms = 1 sec
                Thread.sleep(1000);             // Sleep for 1000 ms = 1 sec
            }
        });
    }

}
