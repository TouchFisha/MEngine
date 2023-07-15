package touchfish.unit.object.component.physics;

import touchfish.unit.math.float3;

/**
 * 接触点数据结构体
 */
public class ContactPoint
{
	/**
	 * 接触点
	 */
	public float3 point;
	/**
	 * 接触点法线
	 */
	public float3 normal;
	/**
	 * 接触点到物体表面距离
	 */
	public float separation;

	public ContactPoint() { }

	public ContactPoint(float3 point, float3 normal, float separation) {
		this.point = point;
		this.normal = normal;
		this.separation = separation;
	}

}
