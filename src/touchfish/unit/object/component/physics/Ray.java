package touchfish.unit.object.component.physics;

import touchfish.unit.math.float3;

import static touchfish.unit.math.math.*;

public class Ray {
    public Ray(float3 origin, float3 direction)
    {
        this.origin = origin;
        this.direction = normalize(direction);
    }

    private float3 origin;

    private float3 direction;

    public float3 getOrigin() {
        return origin;
    }

    public void setOrigin(float3 origin) {
        this.origin = origin;
    }

    public float3 getDirection() {
        return direction;
    }

    public void setDirection(float3 direction) {
        this.direction = normalize(direction);
    }

    public float3 GetPoint(float distance) {
        return add(this.origin, mul(this.direction, distance));
    }

}