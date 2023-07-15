package touchfish.unit.system.event;


import touchfish.unit.action.ActionInOut;
import touchfish.unit.inter.Allocator;
import touchfish.unit.system.thread.RenderThread;

import java.util.*;

public class KeyBehavior{
    public static Map<Integer, KeyBehavior> keys = new HashMap<>();

    public KeyData keyData;
    public boolean isLock;
    private float interval;
    public List<ActionInOut> typeChangedActions;
    private Map<KeyPressType, List<ActionInOut>> onKeysActions;

    public KeyBehavior(int keyCode)
    {
        onKeysActions = new HashMap<>();
        typeChangedActions = new ArrayList<>();
        for (KeyPressType type : KeyPressType.values()) {
            onKeysActions.put(type, new ArrayList<>());
        }
        this.keyData = new KeyData();
        keyData.keyCode = keyCode;
        keyData.lastTime = 0;
        keyData.duration = 0;
        keyData.tempType = KeyPressType.KeyUp;
        keyData.inputType = KeyPressType.non;
        isLock = false;
        interval = 0;
        keys.put(keyData.keyCode, this);
    }

    public void setTempKeyPressType(KeyPressType value){
        keyData.tempType = value;
        if (value == KeyPressType.KeyUp)
        {
            keyData.lastKeyUpTime = RenderThread.ctime;
            keyData.duration = 0;
        }
        List<ActionInOut> actions = onKeysActions.get(value);
        for (Object obj : actions.toArray()) {
            ActionInOut action = (ActionInOut) obj;
            if ((action.invoke() || action.isIgnoreError) && action.isOneShot)
                actions.remove(action);
        }
        for (Object obj : typeChangedActions.toArray()) {
            ActionInOut action = (ActionInOut) obj;
            if ((action.invoke() || action.isIgnoreError) && action.isOneShot)
                typeChangedActions.remove(action);
        }
    }
    public KeyPressType getTempKeyPressType(){
        return  keyData.tempType;
    }
    public static void refreshList(boolean isWaitForKeyUp)
    {
        for (KeyBehavior k : keys.values()) {
            k.refresh(true);
            if (isWaitForKeyUp) k.waitForKeyUp();
        }
    }
    public void refresh(boolean isResetValue)
    {
        if (isResetValue) keyData.reset(keyData.keyCode,0,0,KeyPressType.non,KeyPressType.non);
        for (Object obj : typeChangedActions.toArray()) {
            ActionInOut action = (ActionInOut) obj;
            if (!action.isStaticAction)
                typeChangedActions.remove(action);
        }
        for (Object obj0 : onKeysActions.values().toArray()) {
            List<ActionInOut> actions = (List<ActionInOut>) obj0;
            for (Object obj : actions.toArray()) {
                ActionInOut action = (ActionInOut) obj;
                if (!action.isStaticAction)
                    actions.remove(action);
            }
        }

    }
    public static void clear()
    {
        for (KeyBehavior k : keys.values()) {
            k.clearAllAction();
        }
    }
    public void clearAllAction()
    {
        typeChangedActions.clear();
        for (List<ActionInOut> actions : onKeysActions.values()) {
            actions.clear();
        }
    }
    public void unload()
    {
        keys.remove(keyData.keyCode);
    }
    public void waitForKeyUp()
    {
        isLock = true;
        bind(KeyPressType.KeyUp, ActionInOut.create(
            (args) -> {
                isLock = false;
                return true;
            },
            0, Allocator.Persistent),-1);
    }
    public boolean unbind(KeyPressType keyPressType, ActionInOut action){
        if (keyPressType == KeyPressType.non) return false;
        return onKeysActions.get(keyPressType).remove(action);
    }
    public boolean bind(KeyPressType keyPressType, ActionInOut action)
    {
        return bind(keyPressType,action,-1);
    }
    public boolean bind(KeyPressType keyPressType, ActionInOut keyAction, int index)
    {
        if (keyPressType == KeyPressType.non) return false;
        List<ActionInOut> actions = onKeysActions.get(keyPressType);
        if (index == -1)
        {
            for (Object obj : actions.toArray()) {
                ActionInOut action = (ActionInOut) obj;
                if (action == keyAction)
                    return false;
            }
            actions.add(keyAction);
            return true;
        }
        else
        {
            if (actions.size() < index) return false;
            if (actions.size() == index)
                actions.add(keyAction);
            else actions.set(index, keyAction);
            return true;
        }
    }
    public void run() {
        if (Input.getKey(keyData.keyCode)) KeyPress();
        else if (getTempKeyPressType() != KeyPressType.KeyUp) KeyUp();
    }
    public boolean KeyPress()
    {
        if (isLock) return false;
        interval = RenderThread.ctime - keyData.lastTime;
        keyData.lastTime = RenderThread.ctime;
        if (interval < 0.32f)
        {
            if (interval < 0.03f)
            {
                keyData.duration += interval;
                if (keyData.duration > 0.4f)
                    setTempKeyPressType(keyData.inputType = KeyPressType.HoldOn);
            }
            else setTempKeyPressType(keyData.inputType = KeyPressType.Double);
        }
        else setTempKeyPressType(keyData.inputType = KeyPressType.Click);
        if (keyData.duration == 0) setTempKeyPressType(KeyPressType.KeyDown);
        setTempKeyPressType(KeyPressType.Press);
        return true;
    }
    public boolean KeyUp()
    {
        setTempKeyPressType(KeyPressType.KeyUp);
        return true;
    }
}