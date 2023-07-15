package touchfish.unit.object;

import touchfish.unit.inter.ILifecycle;
import touchfish.unit.system.GameEngine;

import java.util.List;
import java.util.Objects;

public abstract class LifecycleObject extends BaseObject implements ILifecycle {
    public abstract void Logout();

    public boolean active;
    public boolean started;
    public boolean destroyed;
    public int ZBuffer;
    public LifecycleObject() {
        super();
        active = true;
        started = false;
        destroyed = false;
        Register(this);
    }
    public LifecycleObject(String name) {
        super(name);
        active = true;
        started = false;
        destroyed = false;
        Register(this);
    }
    public static void Destroy(LifecycleObject object) {
        GameEngine.Destroy(object);
    }
    public static <T extends LifecycleObject> T Register(T object) {
        return GameEngine.Register(object);
    }

    public static <T extends LifecycleObject> List<T> FindObjectsByType(Class<T> clazz) {
        return GameEngine.FindObjectsByType(clazz);
    }
    public static <T extends LifecycleObject> List<T> FindObjectsByName(String name) {
        return GameEngine.FindObjectsByName(name);
    }
    public static <T extends LifecycleObject> T FindObjectByInstanceID(int instanceID) {
        return GameEngine.FindObjectsByInstanceID(instanceID);
    }
    public void Awake(){}
    public void Start(){}
    public void Update(){}
    public void FixedUpdate(){}
    public void OnDestroy(){}

    @Override
    public boolean equals(Object o) {
        if (destroyed && o == null) return true;
        return super.equals(o);
    }

}
