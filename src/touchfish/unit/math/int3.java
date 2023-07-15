package touchfish.unit.math;

import java.io.Serializable;

public class int3 implements Serializable, Cloneable {
    public int x,y,z;

    public int3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public int3(int fill) {
        this.x = fill;
        this.y = fill;
        this.z = fill;
    }
    public int3() {
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

    private static final int3 zeroInstance = new int3();

    public static int3 zero(){
        return (int3) zeroInstance.clone();
    }


}