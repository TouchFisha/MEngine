package touchfish.unit.system;

import touchfish.unit.annotation.*;
import touchfish.unit.object.component.Camera;
import touchfish.unit.object.game.GameObject;
import touchfish.unit.object.component.render.Renderer;
import touchfish.unit.system.event.Input;
import touchfish.unit.system.event.KeyAxis;
import touchfish.unit.system.event.KeyBehavior;
import touchfish.unit.util.ReflectionUtility;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Application {

    public static boolean active;

    public final static Map<Class, List<Method>> staticMethodMap = new HashMap<>();

    public static void init() {
        GameEngine.root = new GameObject();
        Renderer.mouse1 = new KeyBehavior(MouseEvent.BUTTON1);
        Renderer.mouse2 = new KeyBehavior(MouseEvent.BUTTON2);
        Input.addAxis(new KeyAxis("Vertical", KeyEvent.VK_A, KeyEvent.VK_D, 3, 1, false));
        Input.addAxis(new KeyAxis("Horizontal", KeyEvent.VK_S, KeyEvent.VK_W, 3, 1, false));
        Input.addAxis(new KeyAxis("Shift", KeyEvent.VK_DELETE, KeyEvent.VK_SHIFT, 3, 1, false));

        List<Class> customClasses = ReflectionUtility.getAllClassInPackage("touchfish.script");
        List<Class> customStaticClasses = ReflectionUtility.getAllClassByAnnotationInClasses(customClasses, MEngineStatic.class);

        for (Class c : customStaticClasses) {
            if (!c.getSimpleName().isEmpty()) {
                for (Map.Entry<Class, List<Method>> item : ReflectionUtility.getAllMethodMapByAnnotationAnnotationInClass(c, MEngineRuntimeStaticInterface.class).entrySet()) {
                    if (!staticMethodMap.containsKey(item.getKey())) {
                        staticMethodMap.put(item.getKey(), new ArrayList<>());
                    }
                    staticMethodMap.get(item.getKey()).addAll(item.getValue());
                }
            }
        }

        GameObject mainCamera = new GameObject("MainCamera");
        mainCamera.AddComponent(Camera.class);
        active = true;
    }

    public static void InvokeStaticMethodByAnnotation(Class ann) {
        if (active){
            List<Method> invokes = staticMethodMap.get(ann);
            if (invokes != null) {
                try {
                    for (Method method : staticMethodMap.get(ann)) {
                        method.invoke(null);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void dispose() {
        if (active) {
            GameEngine.dispose();
            InvokeStaticMethodByAnnotation(MEngineDestroy.class);
            active = false;
        }
    }
}
//        GameObject go = new GameObject();
//        go.AddComponent(CircleRenderer.class);
//        go.AddComponent(RectangleRenderer.class);
//        go.AddComponent(RoundRectangleRenderer.class);
//        go.AddComponent(CoroutineThread.class);
//        go.GetComponent(CoroutineThread.class).actions.add(Action.createInvoke(args -> true,3));
//
//        ButtonRenderer button = go.AddComponent(ButtonRenderer.class);
//
//        button.material.mainTexture = new Texture("UI/button");
//        button.textRenderer.text = "单例";
//        button.setEvent(KeyPressType.Click,Action.create(args -> true,0.2f, Allocator.Persistent));
//
//        go.transform.setScale(float3(0.08f,0.05f,0.0f));
//        go.transform.setPosition(float3(0.5f,0.8f,0.0f));