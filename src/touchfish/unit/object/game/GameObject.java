package touchfish.unit.object.game;


import touchfish.unit.annotation.Require;
import touchfish.unit.object.LifecycleObject;
import touchfish.unit.object.component.Component;
import touchfish.unit.object.component.transform.Transform;
import touchfish.unit.util.ReflectionUtility;

import java.lang.reflect.Field;
import java.util.*;

public class GameObject extends LifecycleObject {

    public Map<Class, List<Component>> components = new HashMap<>();
    public Transform transform;

    public GameObject() {
        super();
        transform = AddComponent(Transform.class);
        transform.transform = transform;
    }
    public GameObject(String name) {
        super(name);
        transform = AddComponent(Transform.class);
        transform.transform = transform;
    }
    public <T> T GetComponent(Class<T> t){
        for (Map.Entry<Class, List<Component>> cs : components.entrySet()) {
            if (t.isAssignableFrom(cs.getKey())) {
                if (cs.getValue().size() > 0) {
                    return (T) cs.getValue().get(0);
                }
            }
        }
        return null;
    }
    public <T extends Component> List<T> GetComponents(Class<T> t){
        List<T> res = new ArrayList<>();
        for (Map.Entry<Class, List<Component>> cs : components.entrySet()) {
            if (t.isAssignableFrom(cs.getKey())) {
                for (Component component : cs.getValue()) {
                    res.add((T) component);
                }
                break;
            }
        }
        return res;
    }
    public <T> T AddComponent(Class<T> t){
        T c = null;
        try {
            c = t.newInstance();
            List<Component> cs;
            if (components.containsKey(t)){
                cs = components.get(t);
            }else{
                cs = new ArrayList<>();
                components.put(t,cs);
            }
            Component component = (Component) c;
            cs.add(component);
            component.gameObject = this;
            for (Field field : ReflectionUtility.getAllFieldByAnnotationInClass(t, Require.class)) {
                field.set(component, AddComponent(field.getType()));
            }
            component.Init();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public void Logout() {
        this.active = false;
        for (List<Component> value : components.values()) {
            Iterator<Component> it = value.iterator();
            while (it.hasNext()) {
                Component c = it.next();
                Destroy(c);
                it.remove();
            }
        }
        components = null;
    }

}
