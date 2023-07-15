package touchfish.unit.math;

import java.io.Serializable;

public class boolean3 implements Serializable, Cloneable {
    public boolean x,y,z;

    public boolean3(boolean x, boolean y, boolean z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public boolean3(boolean fill) {
        this.x = fill;
        this.y = fill;
        this.z = fill;
    }
    public boolean3() {
        this.x = false;
        this.y = false;
        this.z = false;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean toBoolean(){
        return x && y && z;
    }
    public void set(boolean value){
        x = y = z = value;
    }
    private static final boolean3 falseInstance = new boolean3();

    public static boolean3 zero(){
        return (boolean3)falseInstance.clone();
    }

}