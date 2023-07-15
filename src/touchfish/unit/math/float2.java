package touchfish.unit.math;

import java.io.Serializable;

public class float2 implements Serializable, Cloneable {
    public float x,y;

    public float2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public float2(float fill) {
        this.x = fill;
        this.y = fill;
    }
    public float2() {
        this.x = 0;
        this.y = 0;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public int2 toInt2(){
        return math.int2((int)x,(int)y);
    }

    private static final float2 zeroInstance = new float2(0,0);
    private static final float2 oneInstance = new float2(1,1);

    public static float2 zero(){
        return (float2)zeroInstance.clone();
    }
    public static float2 one(){
        return (float2)oneInstance.clone();
    }

    @Override
    public String toString() {
        return "float2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}