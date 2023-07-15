package touchfish.unit.math;

import java.io.Serializable;

public class float4x4 implements Serializable, Cloneable {
    public float4 c0, c1, c2, c3;

    public float4x4(float4 c0, float4 c1, float4 c2, float4 c3) {
        this.c0 = c0;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }
    public float4x4(float4 fill) {
        this.c0 = fill;
        this.c1 = fill;
        this.c2 = fill;
        this.c3 = fill;
    }
    public float4x4() {
        this.c0 = float4.zero();
        this.c1 = float4.zero();
        this.c2 = float4.zero();
        this.c3 = float4.zero();
    }

    @Override
    public Object clone() {
        float4x4 res = null;
        try {
            res = (float4x4) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        res.c0 = (float4) c0.clone();
        res.c1 = (float4) c1.clone();
        res.c2 = (float4) c2.clone();
        res.c3 = (float4) c3.clone();
        return res;
    }

    private static final float4x4 zeroInstance = new float4x4(float4.zero(),float4.zero(),float4.zero(),float4.zero());
    private static final float4x4 oneInstance = new float4x4(float4.one(),float4.one(),float4.one(),float4.one());
    private static final float4x4 identityInstance = new float4x4(float4.identity(),float4.identity(),float4.identity(),float4.identity());

    public static float4x4 zero(){
        return (float4x4)zeroInstance.clone();
    }
    public static float4x4 one(){
        return (float4x4)oneInstance.clone();
    }
    public static float4x4 identity(){
        return (float4x4)identityInstance.clone();
    }
}