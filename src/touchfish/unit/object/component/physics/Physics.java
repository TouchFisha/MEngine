package touchfish.unit.object.component.physics;

import touchfish.unit.math.float3;
import touchfish.unit.object.component.Component;

import static touchfish.unit.math.math.*;

import java.util.ArrayList;
import java.util.List;

public class Physics
{
	public static final boolean debug = true;

	public static final List<Collider> colliderList = new ArrayList<>();
	public static List<RaycastHit> RaycastAll(Ray ray, float distance)
	{
		List<RaycastHit> res = new ArrayList<>();
		for (Collider collider : colliderList) {
			float3 hitPoint = collider.RayCaseBounds(ray, distance);
			if (hitPoint != null) {
				res.add(
						new RaycastHit(
								collider.instanceID,
								hitPoint,
								inverse(ray.getDirection()),
								length(sub(hitPoint, ray.getOrigin())),
								-1));
			}
		}
		return res;
	}
	public static List<RaycastHit> RaycastAll(Ray ray)
	{
		List<RaycastHit> res = new ArrayList<>();
		for (Collider collider : colliderList) {
			float3 hitPoint = collider.RayCaseBounds(ray);
			if (hitPoint != null) {
				res.add(
						new RaycastHit(
								collider.instanceID,
								hitPoint,
								inverse(ray.getDirection()),
								length(sub(hitPoint, ray.getOrigin())),
								-1));
			}
		}
		return res;
	}

	public static void RelativeCollision(Collider src, Collider dest, float3 srcVelocity, float3 destVelocity) {
		if (src.bounds.Intersects(dest.bounds)) {

			if (debug) {
				System.out.println(src.name + " Intersects " + dest.name);
			}


			Collision[] collisions = Bounds.Collision(src.bounds,dest.bounds);
			if (collisions != null) {
				Collision srcCollision  = collisions[0];
				Collision destCollision  = collisions[1];

				srcCollision.collider = dest;
				destCollision.collider = src;

				srcCollision.gameObject = dest.gameObject;
				destCollision.gameObject = src.gameObject;

				srcCollision.transform = dest.transform;
				destCollision.transform = src.transform;


				srcCollision.relativeVelocity = sub(srcVelocity, destVelocity);
				destCollision.relativeVelocity = inverse(srcCollision.relativeVelocity);

				for (List<Component> value : src.gameObject.components.values()) {
					for (Component component : value) {
						component.OnCollisionStay(srcCollision);
					}
				}

				for (List<Component> value : dest.gameObject.components.values()) {
					for (Component component : value) {
						component.OnCollisionStay(destCollision);
					}
				}
			}
		}
	}

	public static void Collide(Collider src, Collider dest) {

		if (src.rigidbody != null) {
			if (dest.rigidbody != null) {
				RelativeCollision(src,dest,src.rigidbody.velocity,dest.rigidbody.velocity);
			} else {
				RelativeCollision(src,dest,src.rigidbody.velocity,float3.zero());
			}
		} else if (dest.rigidbody != null) {
			RelativeCollision(dest,src,dest.rigidbody.velocity,float3.zero());
		}
	}

	public static void Update() {
		for (int i = 0, colliderListSize = colliderList.size(); i < colliderListSize; i++) {
			Collider src = colliderList.get(i);
			for (int j = i+1; j < colliderListSize; j++) {
				Collider dest = colliderList.get(j);
				if (debug) {
					System.out.println(src.name + " -> " + dest.name);
				}
				Collide(src,dest);
			}
		}
	}
}