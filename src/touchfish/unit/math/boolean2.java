package touchfish.unit.math;

import java.io.Serializable;

public class boolean2 implements Serializable, Cloneable {
    public boolean x,y;

    public boolean2(boolean x, boolean y) {
        this.x = x;
        this.y = y;
    }
    public boolean2(boolean fill) {
        this.x = fill;
        this.y = fill;
    }
    public boolean2() {
        this.x = false;
        this.y = false;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getAll(){
        return x && y;
    }
    public void set(boolean value){
        x = y = value;
    }

    private static final boolean2 falseInstance = new boolean2();

    public static boolean2 zero(){
        return (boolean2) falseInstance.clone();
    }

}