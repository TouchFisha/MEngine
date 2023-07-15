package touchfish.unit.math;

import java.io.Serializable;

public class float3x3 implements Serializable, Cloneable {
    public float3 c0, c1, c2;

    public float3x3(float3 c0, float3 c1, float3 c2) {
        this.c0 = c0;
        this.c1 = c1;
        this.c2 = c2;
    }
    public float3x3(float3 fill) {
        this.c0 = fill;
        this.c1 = fill;
        this.c2 = fill;
    }
    public float3x3() {
        this.c0 = float3.zero();
        this.c1 = float3.zero();
        this.c2 = float3.zero();
    }

    @Override
    public Object clone() {
        float3x3 res = null;
        try {
            res = (float3x3) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        res.c0 = (float3) c0.clone();
        res.c1 = (float3) c1.clone();
        res.c2 = (float3) c2.clone();
        return res;
    }

    private static final float3x3 zeroInstance = new float3x3(float3.zero(),float3.zero(),float3.zero());
    private static final float3x3 oneInstance = new float3x3(float3.one(),float3.one(),float3.one());

    public static float3x3 zero(){
        return (float3x3)zeroInstance.clone();
    }
    public static float3x3 one(){
        return (float3x3)oneInstance.clone();
    }
}