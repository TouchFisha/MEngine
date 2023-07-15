package touchfish.unit.math;

import java.io.Serializable;

public class int4 implements Serializable, Cloneable {
    public int x,y,z,w;

    public int4(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    public int4(int fill) {
        this.x = fill;
        this.y = fill;
        this.z = fill;
        this.w = fill;
    }
    public int4() {
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

    private static final int4 zeroInstance = new int4();

    public static int4 zero(){
        return (int4) zeroInstance.clone();
    }

}