package touchfish.unit.math;

import java.io.Serializable;

public class int2 implements Serializable, Cloneable {
    public int x,y;

    public int2(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int2(int fill) {
        this.x = fill;
        this.y = fill;
    }
    public int2() {
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

    private static final int2 zeroInstance = new int2();

    public static int2 zero(){
        return (int2) zeroInstance.clone();
    }

}