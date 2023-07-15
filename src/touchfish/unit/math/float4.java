package touchfish.unit.math;

import java.io.Serializable;

public class float4 implements Serializable, Cloneable {
    public float x,y,z,w;

    public float4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    public float4(float fill) {
        this.x = fill;
        this.y = fill;
        this.z = fill;
        this.w = fill;
    }
    public float4() {
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

    private static final float4 zeroInstance = new float4(0,0,0,0);
    private static final float4 oneInstance = new float4(1,1,1,1);
    private static final float4 identityInstance = new float4(0,0,0,1);

    public static float4 zero(){
        return (float4)zeroInstance.clone();
    }
    public static float4 one(){
        return (float4)oneInstance.clone();
    }
    public static float4 identity(){
        return (float4)identityInstance.clone();
    }
}