package touchfish.unit.system;

import touchfish.unit.action.ActionUtility;
import touchfish.unit.inter.IDelegate;
import touchfish.unit.object.LifecycleObject;
import touchfish.unit.object.game.GameObject;

import java.util.HashMap;
import java.util.Map;

public class Coroutine {
    public final static ActionUtility actionUtility = new ActionUtility();

    public static void Invoke(IDelegate delegate, float time){
        actionUtility.Invoke(delegate, time);
    }

    public static void Repeat(IDelegate delegate, float time){
        actionUtility.Repeat(delegate, time);
    }

    public static void Update() {
        actionUtility.Update();
    }
}
