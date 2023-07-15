package touchfish.unit.action;

import touchfish.unit.inter.Allocator;
import touchfish.unit.inter.IDelegate;

import java.util.ArrayList;
import java.util.List;

public class ActionUtility {

    public List<Action> acts = new ArrayList<>();

    public ActionUtility(){}

    public void Invoke(IDelegate delegate, float time){
        acts.add(Action.createInvoke(delegate, time));
    }

    public void Repeat(IDelegate delegate, float time){
        acts.add(Action.create(delegate, time, Allocator.Persistent));
    }

    public void Update(){
        for (Object o : acts.toArray()) {
            Action action = (Action) o;
            if (action.invoke()) {
                acts.remove(action);
            }
        }
    }

}
