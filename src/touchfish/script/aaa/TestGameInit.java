package touchfish.script.aaa;

import touchfish.unit.action.Action;
import touchfish.unit.action.ActionInOut;
import touchfish.unit.annotation.*;
import touchfish.unit.inter.Allocator;
import touchfish.unit.math.float3;
import touchfish.unit.object.assets.Material;
import touchfish.unit.object.assets.Texture;
import touchfish.unit.object.component.physics.Collider;
import touchfish.unit.object.component.physics.Rigidbody;
import touchfish.unit.object.component.render.Renderer;
import touchfish.unit.object.component.render.TextureRenderer;
import touchfish.unit.object.component.render.ui.ButtonRenderer;
import touchfish.unit.object.component.transform.ScaledType;
import touchfish.unit.object.game.GameObject;
import touchfish.unit.system.event.KeyPressType;

@MEngineStatic
public class TestGameInit {
    public static TextureRenderer r;
    @MEngineAwake
    public static void lalala() {
        GameObject go = new GameObject("SB");
        go.AddComponent(TestBehaviour.class);

        GameObject floor = new GameObject("Floor");
        floor.AddComponent(TextureRenderer.class).setMaterial(new Material("background/dark"));
        floor.AddComponent(Collider.class);
        floor.transform.scaledType = ScaledType.Screen;
        floor.transform.setScale(new float3(0.5f, 0.3f, 1f));
        floor.transform.setPosition(new float3(100f, 300f, 1f));


    }
    @MEngineStart
    public static void lalala1() {
    }
    @MEngineUpdate
    public static void lalala2() {

    }
    @MEngineUpdate
    public static void lalal2a2() {
    }
    @MEngineUpdate
    public static void lala3la2() {
    }
    @MEngineUpdate
    public static void lala4la2() {
    }
    @MEngineUpdate
    public static void lalal5a2() {
    }
    @MEngineFixedUpdate
    public static void lalala3() {
    }
    @MEngineDestroy
    public static void lalala4() {
    }

}
