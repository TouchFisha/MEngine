package touchfish.unit.system.event;

import touchfish.unit.math.math;
import touchfish.unit.object.BaseObject;
import touchfish.unit.system.thread.RenderThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Input {
    private final static List<Integer> inputBuffer = new ArrayList<>();
    private final static List<Integer> inputs = new ArrayList<>();
    private final static Map<String,KeyAxis> axisMap = new HashMap<>();
    public static boolean getKey(int keyCode) {
        return inputs.contains(keyCode);
    }
    public static void press(Integer keyCode) {
        if (!inputBuffer.contains(keyCode)) {
            inputBuffer.add(keyCode);
        }
    }
    public static void keyup(Integer keyCode) {
        inputBuffer.remove(keyCode);
    }
    public static void preload() {
        inputs.clear();
        inputs.addAll(inputBuffer);
    }
    public static float getAxis(String name) {
        return axisMap.get(name).value;
    }
    public static void addAxis(KeyAxis axis) {
        axisMap.put(axis.name, axis);
    }
    public static void Update() {
        preload();
        for (KeyAxis value : axisMap.values()) {
            value.Update();
        }
    }
}
