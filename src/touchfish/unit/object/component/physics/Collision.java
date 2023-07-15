package touchfish.unit.object.component.physics;

import touchfish.unit.math.float3;
import touchfish.unit.object.component.transform.Transform;
import touchfish.unit.object.game.GameObject;

/**
 * 碰撞数据结构体
 */
public class Collision
{
	public float3 relativeVelocity;
	public Rigidbody rigidbody;
	public Collider collider;
	public Transform transform;
	public GameObject gameObject;
	public int contactCount;
	public ContactPoint[] contacts;
	public float3 impulse;
	public Bounds intersection;

	public float getIntersectionVolume() {
		float3 intersectBoundsSize = intersection.GetSize();
		return intersectBoundsSize.x * intersectBoundsSize.y * intersectBoundsSize.z;
	}

	public Collision() { }

	public Collision(float3 relativeVelocity, Collider collider, int contactCount, ContactPoint[] contacts, float3 impulse) {
		this.relativeVelocity = relativeVelocity;
		this.rigidbody = collider.rigidbody;
		this.collider = collider;
		this.transform = collider.transform;
		this.gameObject = collider.gameObject;
		this.contactCount = contactCount;
		this.contacts = contacts;
		this.impulse = impulse;
	}
}
