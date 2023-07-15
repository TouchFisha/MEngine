package touchfish.unit.object.component.physics;

import touchfish.unit.math.float3;
import touchfish.unit.object.LifecycleObject;

public class RaycastHit {
    private int colliderInstanceID;
    public Collider collider(){
        return LifecycleObject.FindObjectByInstanceID(this.colliderInstanceID);
    }
    public float3 point;
    public float3 normal;
    public float distance;
    public int triangleIndex;
    public Rigidbody rigidbody() {
        return collider().rigidbody;
    }
    public RaycastHit() {}

    public RaycastHit(int colliderInstanceID, float3 point, float3 normal, float distance, int triangleIndex) {
        this.colliderInstanceID = colliderInstanceID;
        this.point = point;
        this.normal = normal;
        this.distance = distance;
        this.triangleIndex = triangleIndex;
    }
}