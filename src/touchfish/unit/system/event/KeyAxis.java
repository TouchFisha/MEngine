package touchfish.unit.system.event;

import touchfish.unit.math.math;
import touchfish.unit.object.BaseObject;
import touchfish.unit.system.thread.RenderThread;

public class KeyAxis extends BaseObject {
    int keyCode0;
    int keyCode1;
    float speed;
    float valueMax;
    float value;
    float t;
    boolean gradient;

    public KeyAxis(String name, int keyCode0, int keyCode1, float speed, float valueMax, boolean gradient) {
        super(name);
        this.keyCode0 = keyCode0;
        this.keyCode1 = keyCode1;
        this.speed = speed;
        this.valueMax = valueMax;
        this.gradient = gradient;
    }

    public void toCenter(float speed) {
        if (math.abs(t - 0.5f) < 0.01f) {
            t = 0.5f;
        } else {
            t += speed * (t > 0.5f ? -RenderThread.ftime : RenderThread.ftime);
        }
    }
    public void toRight(float speed) {
        if (t > 1.01f) {
            t = 1f;
        } else {
            t += t < 0.5f ? speed * RenderThread.ftime * 2 : speed * RenderThread.ftime;
        }
    }
    public void toLeft(float speed) {
        if (t < 0.01f) {
            t = 0f;
        } else {
            t -= t > 0.5f ? speed * RenderThread.ftime * 2 : speed * RenderThread.ftime;
        }
    }

    public void Update() {
        if (gradient) {
            if (Input.getKey(keyCode0)) {
                if (Input.getKey(keyCode1)) {
                    value = math.lerp(value, 0, speed);
                } else {
                    value = math.lerp(value, -valueMax, speed);
                }
            } else if (Input.getKey(keyCode1)) {
                value = math.lerp(value, valueMax, speed);
            } else {
                value = math.lerp(value, 0, speed);
            }
        } else {
            if (Input.getKey(keyCode0)) {
                if (Input.getKey(keyCode1)) {
                    toCenter(speed);
                    value = math.lerp(-valueMax, valueMax, t);
                } else {
                    toLeft(speed);
                    value = math.lerp(-valueMax, valueMax, t);
                }
            } else if (Input.getKey(keyCode1)) {
                toRight(speed);
                value = math.lerp(-valueMax, valueMax, t);
            } else {
                toCenter(speed);
                value = math.lerp(-valueMax, valueMax, t);
            }
        }
    }

}