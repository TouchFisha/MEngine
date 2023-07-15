package touchfish.unit.object.component.physics;

import touchfish.unit.math.float3;
import touchfish.unit.object.component.Component;
import touchfish.unit.object.component.transform.ForceMode;
import touchfish.unit.system.thread.RenderThread;

import static touchfish.unit.math.math.*;
public class Rigidbody extends Component {
    public static final float MinSpeed = 0.0001f;

    public float speed = 0;
    public float3 velocity = float3.zero();
    public float3 acceleration = float3.zero();
    public float3 angularVelocity = float3.zero();
    public float drag = 2f;
    public float angularDrag = 1f;
    public float mass = 1f;
    public float gravity = 0.98f;
    public boolean useGravity = true;
    public boolean freezeRotation = false;
    public float3 centerOfMass = float3.zero();
    public float3 worldCenterOfMass = float3.zero();
    public float3 position = float3.zero();
    public float3 rotation = float3.zero();
    public float friction = 1;
    public float fs = 0;

    public float3 FrictionAcceleration() {
        // u * m * g = f 摩擦力
        // f / m = a 加速度
        // a * t = v 速度
        // u * m * g / m = a = u * g 摩擦力加速度
        // ((drag * mass * gravity) * -v.normalized) / mass =  drag * gravity * -v.normalized = acceleration
        if (speed > MinSpeed) {
            float3 res = mul(normalize(inverse(velocity)), (friction * drag * gravity * mass));
            if (!Float.isNaN(res.x) && !Float.isNaN(res.y) && !Float.isNaN(res.z)) {
                fs = length(res);
                if (fs > speed) {
                    return float3.zero();
                } else {
                    return res;
                }
            }
        }
        return float3.zero();
    }
    public float3 Acceleration(float3 force) {
        return div(force, mass);
    }
    public void AddExplosionForce(float explosionForce, float3 explosionPosition, float explosionRadius, float upwardsModifier) {

    }

    public void AddForce(float3 force, ForceMode mode) {
        acceleration = add(Acceleration(force), FrictionAcceleration());
        velocity = add(velocity, mul(acceleration, RenderThread.ftime));
    }
    public void AddForce(float3 force) {
        AddForce(force, ForceMode.Force);
    }
    public void AddRelativeForce(float3 force, ForceMode mode) {

    }
    public void AddRelativeForce(float3 force) {

    }
    public void AddRelativeTorque(float3 torque, ForceMode mode) {

    }
    public void AddRelativeTorque(float3 torque) {

    }
    public void AddTorque(float3 torque) {

    }
    public void AddTorque(float3 torque, ForceMode mode) {

    }
    public float3 GetPointVelocity(float3 worldPoint) {

        return null;
    }
    public float3 GetRelativePointVelocity(float3 relativePoint) {

        return null;
    }

    @Override
    public void FixedUpdate() {
        speed = length(velocity) / RenderThread.ftime;
        if (speed >= fs && speed > MinSpeed) {
            acceleration = FrictionAcceleration();
            velocity = add(velocity, mul(acceleration, RenderThread.ftime));

            switch (transform.locateType) {

                case Origin:
                    break;
                case Screen:
                    break;
                case Object:
                    transform.Translate(velocity);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + transform.locateType);
            }

        } else {
            velocity = float3.zero();
        }
    }
}