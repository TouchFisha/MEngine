package touchfish.script.aaa;


import touchfish.unit.annotation.Require;
import touchfish.unit.math.float2;
import touchfish.unit.math.float3;
import touchfish.unit.math.math;
import touchfish.unit.object.assets.Material;
import touchfish.unit.object.component.Behavior;
import touchfish.unit.object.component.physics.Collider;
import touchfish.unit.object.component.physics.Collision;
import touchfish.unit.object.component.render.anim.AnimationGroupData;
import touchfish.unit.object.component.render.anim.Animation;
import touchfish.unit.object.component.physics.Rigidbody;
import touchfish.unit.system.event.Input;
import touchfish.unit.util.ProcessRunner;

import java.awt.event.KeyEvent;
import static touchfish.unit.math.math.*;


/**
 * 主角是一个偶然诞生的拥有理智的噩梦衍生物想保护一个被机械改造的人类女孩
 * 主角杀人变强，理智降低，不杀人变弱，理智不变
 */

public class TestBehaviour extends Behavior {
    public float baseSpeed = 5f;
    public float runSpeedAdd = 0.2f;
    @Require
    public Animation animation;
    @Require
    public Rigidbody rigidbody;
    @Require
    public Collider collider;

    public static ProcessRunner process;

    @Override
    public void Start() {
        collider.size = float3(0.5f,0.9f,1f);
        animation.setMaterial(new Material("player/a4c/iWen"));
        animation.AddAnimData(
                new AnimationGroupData(KeyEvent.VK_S, 4, 0.8f),
                new AnimationGroupData(KeyEvent.VK_A, 4, 0.8f),
                new AnimationGroupData(KeyEvent.VK_D, 4, 0.8f),
                new AnimationGroupData(KeyEvent.VK_W, 4, 0.8f)
        );
    }

    @Override
    public void Update() {
        float2 move = float2(Input.getAxis("Vertical"), Input.getAxis("Horizontal"));
        float shift = Input.getAxis("Shift");
        float walkAxis = abs(((abs(move.x) > abs(move.y)) ? move.x : move.y));
        float runAxis = walkAxis * shift;
        float speed = (walkAxis + (runAxis * runSpeedAdd)) * baseSpeed;
        float angle = atan2(-move.y, move.x) * math.Rad2Deg;
        float3 forward = rotate(rotateZ(angle), float3.right());
        rigidbody.AddForce(mul(forward, speed));
        animation.Auto(rigidbody.speed / 100f);

        if (Input.getKey(KeyEvent.VK_O)) {
            if (process == null) {
                process = new ProcessRunner();
                process.enableOutputReader = true;
                process.start("E:\\PyCharm\\PythonProjects\\dsau\\natural_language_processing\\dist\\web\\web.exe");
            }
        }
        if (Input.getKey(KeyEvent.VK_P)) {
            if (process != null) {
                process.dispose();
                process = null;
            }
        }
    }

    @Override
    public void Awake() {

    }

    @Override
    public void FixedUpdate() {

    }

    @Override
    public void OnCollisionStay(Collision collision) {
    }

    @Override
    public void OnDestroy() {

    }
}



