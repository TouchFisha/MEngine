package touchfish.unit.system.event;

import java.awt.event.KeyEvent;

public class KeyData {

    public String keyName;
    public int keyCode;
    public float lastTime;
    public float lastKeyUpTime;
    public float duration;
    public KeyPressType tempType;
    public KeyPressType inputType;
    public void reset(int keyCode, float lastTime, float duration, KeyPressType tempType, KeyPressType keyType) {
        this.keyCode = keyCode;
        this.keyName = KeyEvent.getKeyText(keyCode);
        this.lastTime = lastTime;
        this.duration = duration;
        this.tempType = tempType;
        this.inputType = keyType;
        lastKeyUpTime = 0;
    }
}