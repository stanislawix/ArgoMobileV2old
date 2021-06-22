#ROBOT MACHINGE CONFIGURATION
#The localhost IP address = IP address for the Master node
export ROS_MASTER_URI=http://localhost:11311
#The IP address for the Master node
export ROS_HOSTNAME=192.168.0.152
export ROS_IP=192.168.0.152

echo "ROS_HOSTNAME: "$ROS_HOSTNAME
echo "ROS_IP: "$ROS_IP
echo "ROS_MASTER_URI: "$ROS_MASTER_URI



#WORKSTATION CONFIGURATION
#IP address for the Master node
export ROS_MASTER_URI=http://192.168.0.152:11311
#Own IP addressput
export ROS_HOSTNAME=192.168.0.15
export ROS_IP=192.168.0.15

echo "ROS_HOSTNAME: "$ROS_HOSTNAME
echo "ROS_IP: "$ROS_IP
echo "ROS_MASTER_URI: "$ROS_MASTER_URI