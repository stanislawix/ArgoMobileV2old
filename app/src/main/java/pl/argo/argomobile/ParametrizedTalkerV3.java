package pl.argo.argomobile;

import android.util.Log;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;  // This library give us the AbstractNodeMain interface (see ahead)
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;  // Import the publisher

//import lombok.Data;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * A simple {@link Publisher} {@link NodeMain}.
 */

@Data
public class ParametrizedTalkerV3 extends AbstractNodeMain { // Java nodes NEEDS to implement AbstractNodeMain
    Vector3Implemenation linear = new Vector3Implemenation();
    Vector3Implemenation angular = new Vector3Implemenation();

    private geometry_msgs.Twist twist;

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava/talker");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        linear = new Vector3Implemenation();
        //final Publisher<geometry_msgs.Twist> publisher = connectedNode.newPublisher("/turtle1/cmd_vel", geometry_msgs.Twist._TYPE); // That's how you create a publisher in Java!
        final Publisher<geometry_msgs.Twist> publisher = connectedNode.newPublisher("cmd_vel", "geometry_msgs/Twist"); // That's how you create a publisher in Java!

        // This CancellableLoop will be canceled automatically when the node shuts
        // down.
        connectedNode.executeCancellableLoop(new CancellableLoop() {

            @Override
            protected void setup() {
            }

            @Override
            protected void loop() throws InterruptedException {
                twist = publisher.newMessage(); // Init a msg variable that of the publisher type

                twist.getLinear().setX(linear.getX());
                twist.getLinear().setY(linear.getY());
                twist.getLinear().setZ(linear.getZ());

                if(linear.getX() != 0 || linear.getY() != 0 || linear.getZ() != 0) {
                    publisher.publish(twist);       // Publish the message (if running use rostopic list to see the message)
                }

                Thread.sleep(10);             // Sleep for 1000 ms = 1 sec
            }
        });
    }

}
