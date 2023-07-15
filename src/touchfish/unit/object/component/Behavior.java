package touchfish.unit.object.component;

import touchfish.unit.action.*;
import touchfish.unit.inter.Allocator;
import touchfish.unit.inter.IDelegate;

import java.util.ArrayList;
import java.util.List;

public abstract class Behavior extends Component {

    private ActionUtility actionUtility;

    public boolean enable;

    public Behavior() {
        super();
        enable = true;
        actionUtility = new ActionUtility();
    }

    public boolean isActiveAndEnabled() {
        return active && enable;
    }

    public void print(Object message) {
        System.out.println(message);
    }

    public void Invoke(IDelegate delegate, float time){
        actionUtility.Invoke(delegate, time);
    }

    public void Repeat(IDelegate delegate, float time){
        actionUtility.Repeat(delegate, time);
    }

    @Override
    public void FixedUpdate() {
        actionUtility.Update();
    }
}
