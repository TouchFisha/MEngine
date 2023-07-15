package touchfish.unit.object.component.render;


import touchfish.unit.action.ActionInOut;
import touchfish.unit.inter.UIEventState;
import touchfish.unit.object.assets.Material;
import touchfish.unit.system.event.KeyPressType;

import java.util.HashMap;
import java.util.Map;

public class TextureRenderer extends Renderer {

    Map<KeyPressType, ActionInOut> actionMap;
    UIEventState state;
    public boolean mouseInside;

    public TextureRenderer(){
        super();
        actionMap = new HashMap<>();
        state = UIEventState.Outside;
        material = new Material();
        for (KeyPressType value : KeyPressType.values()) {
            actionMap.put(value,null);
        }
    }
    public UIEventState getState() {
        return state;
    }
    public ActionInOut getEvent(KeyPressType type){
        return actionMap.get(type);
    }
    public boolean setEvent(KeyPressType type, ActionInOut action){
        if (state == UIEventState.Inside) {
            unload();
        }
        actionMap.replace(type,action);
        if (state == UIEventState.Inside) {
            upload();
        }
        return true;
    }
    public void upload(){
        for (KeyPressType k : actionMap.keySet()) {
            ActionInOut action = actionMap.get(k);
            if (action != null){
                mouse1.bind(k,action);
            }
        }
    }
    public void unload(){
        for (KeyPressType k : actionMap.keySet()) {
            ActionInOut action = actionMap.get(k);
            if (action != null){
                mouse1.unbind(k,action);
            }
        }
    }
    public void setState(UIEventState value) {
        if (this.state != value){
            this.state = value;
            if (value == UIEventState.Inside){
                upload();
            } else {
                unload();
            }
        }
    }

    @Override
    public void Update() {
        super.Update();
        mouseInside = mouse.x > positionPixel.x && mouse.x < positionPixel.x + scalePixel.x && mouse.y > positionPixel.y && mouse.y < positionPixel.y + scalePixel.y;
        if (mouseInside) {
            setState(UIEventState.Inside);
        }else{
            setState(UIEventState.Outside);
        }
        Render();
    }

    public void Render() {
        graphics2D.drawImage(material.mainTexture.getImage(), graphicsTransform,null);
    }

}
