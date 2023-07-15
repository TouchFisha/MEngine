package touchfish.unit.object.component;

import touchfish.unit.inter.Initialization;
import touchfish.unit.object.component.physics.Collision;
import touchfish.unit.object.game.GameObject;
import touchfish.unit.object.LifecycleObject;
import touchfish.unit.object.component.transform.Transform;

import java.lang.reflect.InvocationTargetException;

public abstract class Component extends LifecycleObject implements Initialization {

    public GameObject gameObject;
    public Transform transform;
    public String tag;

    public Component() {
        super();
    }

    @Override
    public void Logout(){
        gameObject = null;
        transform = null;
    }

    @Override
    public void Init(){
        this.name = gameObject.name;
        this.transform = gameObject.transform;
    }

    public void SendMessage(String methodName, Object ... value) {
        try {
            getClass().getDeclaredMethod(methodName, null).invoke(this, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public <T extends Component> T AddComponent(Class<T> clazz) {
        return gameObject.AddComponent(clazz);
    }

    public <T extends Component> T GetComponent(Class<T> clazz) {
        return gameObject.GetComponent(clazz);
    }

    public void OnCollisionStay(Collision collision) { }

}
