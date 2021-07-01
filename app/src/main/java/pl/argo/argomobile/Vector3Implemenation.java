package pl.argo.argomobile;

import org.ros.internal.message.RawMessage;
import geometry_msgs.Vector3;

public class Vector3Implemenation implements Vector3 {
    private double x, y, z;

    public Vector3Implemenation() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3Implemenation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public RawMessage toRawMessage() {
        return null;
    }
}
