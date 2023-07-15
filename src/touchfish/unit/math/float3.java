package touchfish.unit.math;

import java.io.Serializable;

public class float3 implements Serializable, Cloneable {
    public float x,y,z;

    public float3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public float3(int3 xyz) {
        this.x = xyz.x;
        this.y = xyz.y;
        this.z = xyz.z;
    }
    public float3(float fill) {
        this.x = fill;
        this.y = fill;
        this.z = fill;
    }
    public float3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public int3 toInt3(){
        return math.int3((int)x,(int)y,(int)z);
    }

    private static final float3 zeroInstance = new float3(0,0,0);
    private static final float3 oneInstance = new float3(1,1,1);
    private static final float3 upInstance = new float3(0,1,0);
    private static final float3 forwardInstance = new float3(0,0,1);
    private static final float3 rightInstance = new float3(1,0,0);

    public static float3 zero(){
        return (float3)zeroInstance.clone();
    }
    public static float3 one(){
        return (float3)oneInstance.clone();
    }
    public static float3 up(){
        return (float3)upInstance.clone();
    }
    public static float3 forward(){
        return (float3)forwardInstance.clone();
    }
    public static float3 right(){
        return (float3)rightInstance.clone();
    }

    @Override
    public String toString() {
        return "float3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}