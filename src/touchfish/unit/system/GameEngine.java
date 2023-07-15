package touchfish.unit.system;

import touchfish.unit.annotation.Require;
import touchfish.unit.object.LifecycleObject;
import touchfish.unit.object.component.Behavior;
import touchfish.unit.object.component.Component;
import touchfish.unit.object.game.GameObject;
import touchfish.unit.util.ReflectionUtility;

import java.lang.reflect.Field;
import java.util.*;

public class GameEngine {
    public static int instanceCount = 0;
    public static Map<Integer, LifecycleObject> objectMap = new HashMap<>();
    public static GameObject root;
    public static void Destroy(LifecycleObject object) {
        if (object == null || object.destroyed) return;
        if (objectMap.containsKey(object.instanceID)){
            objectMap.remove(object.instanceID);
        }
        object.OnDestroy();
        object.destroyed = true;
        object.Logout();
    }
    public static <T extends LifecycleObject> T Register(T object){
        if (object.instanceID == -1) {
            object.instanceID = instanceCount;
            objectMap.put(instanceCount,object);
            instanceCount++;
            object.Awake();
            Coroutine.Invoke(()->
            {
                if (!object.destroyed && !object.started) {
                    object.Start();
                    object.started = true;
                }
            },0.1f);
        }
        return object;
    }


    public static void dispose() {
        if (Application.active) {
            for (LifecycleObject value : new HashSet<>(objectMap.values())) {
                if (!value.destroyed)
                    GameEngine.Destroy(value);
            }
        }
    }

    public static <T extends LifecycleObject> List<T> FindObjectsByType(Class<T> clazz) {
        List<T> res = new ArrayList<>();
        for (LifecycleObject value : objectMap.values()) {
            if (value.getClass() == clazz) {
                res.add((T) value);
            }
        }
        return res;
    }

    public static <T extends LifecycleObject> List<T> FindObjectsByName(String name) {
        List<T> res = new ArrayList<>();
        for (LifecycleObject value : objectMap.values()) {
            if (value.name.equals(name)) {
                res.add((T) value);
            }
        }
        return res;
    }

    public static <T extends LifecycleObject> T FindObjectsByInstanceID(int instanceID) {
        for (LifecycleObject value : objectMap.values()) {
            if (value.instanceID == instanceID) {
                return (T) value;
            }
        }
        return null;
    }
}
