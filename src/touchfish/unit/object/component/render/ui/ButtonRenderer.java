package touchfish.unit.object.component.render.ui;

import touchfish.unit.math.float3;
import touchfish.unit.object.game.GameObject;
import touchfish.unit.object.component.render.TextRenderer;
import touchfish.unit.object.component.render.TextureRenderer;

public class ButtonRenderer extends TextureRenderer {

    public TextRenderer textRenderer;

    public ButtonRenderer(){
        super();
        GameObject t = new GameObject();
        t.name = "Text";
        textRenderer = t.AddComponent(TextRenderer.class);
        t.transform.alignCenter.set(true);
    }

    @Override
    public void Init() {
        super.Init();
        textRenderer.transform.SetParent(transform);
    }

    @Override
    public void Update() {
        super.Update();
        float3 p = transform.getPosition();
        p.z+=1;
        textRenderer.transform.setPosition(p);
        textRenderer.transform.setScale(transform.getScale());
        textRenderer.transform.setRotation(transform.getRotation());
    }
}
