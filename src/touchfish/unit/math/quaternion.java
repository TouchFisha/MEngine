package touchfish.unit.math;

import java.io.Serializable;

public class quaternion implements Serializable, Cloneable {
    public float x, y, z, w;

    public quaternion(float x, float y, float z,float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    private quaternion(float fill) {
        this.x = fill;
        this.y = fill;
        this.z = fill;
        this.w = fill;
    }
    private quaternion() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public int4 toInt4(){
        return math.int4((int)x,(int)y,(int)z,(int)w);
    }
    public float4 toFloat4(){
        return math.float4(x,y,z,w);
    }

    private static final quaternion zeroInstance = new quaternion(0,0,0,0);
    private static final quaternion oneInstance = new quaternion(1,1,1,1);
    private static final quaternion identityInstance = new quaternion(0,0,0,1);

    public static quaternion zero(){
        return (quaternion)zeroInstance.clone();
    }
    public static quaternion one(){
        return (quaternion)oneInstance.clone();
    }
    public static quaternion identity(){
        return (quaternion)identityInstance.clone();
    }


    @Override
    public String toString() {
        return "quaternion{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
}