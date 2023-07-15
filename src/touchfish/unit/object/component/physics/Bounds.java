package touchfish.unit.object.component.physics;

import touchfish.unit.math.float3;
import touchfish.unit.system.Debug;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static touchfish.unit.math.math.*;

public class Bounds {

    public static float3 IntersectRayAABB(Ray ray, Bounds aabb) {
        return IntersectRayAABB(ray.getOrigin(), ray.getDirection(), Float.MAX_VALUE, aabb);
    }
    public static float3 IntersectRayAABB(Ray ray, float distance, Bounds aabb) {
        return IntersectRayAABB(ray.getOrigin(), ray.getDirection(), distance, aabb);
    }
    public static float3 IntersectRayAABB(float3 rayOrigin, float3 rayDirection, float distance, Bounds aabb) {
        float3 tMin = div(sub(aabb.GetMin(), rayOrigin), rayDirection);
        float3 tMax = div(sub(aabb.GetMax(), rayOrigin), rayDirection);

        float3 t1 = min(tMin, tMax);
        float3 t2 = max(tMin, tMax);

        float tNear = max(max(t1.x, t1.y), t1.z);
        float tFar = min(min(t2.x, t2.y), t2.z);

        if (tNear > tFar || tFar < 0 || tNear > distance)
            return null;

        return add(rayOrigin, mul(rayDirection, tNear));
    }


    private float3 extents;
    public float3 center;
    private float3 min;
    private float3 max;
    public Bounds(float3 center, float3 size) {
        this.extents = mul(size,0.5f);
        this.center = add(center, extents);
        //this.center = center;
        ReloadMinMax();
    }
    public Bounds() {
        this.center = float3.zero();
        this.extents = mul(float3.zero(),0.5f);
        ReloadMinMax();
    }

    public float GetVolume(){
        float3 size = GetSize();

        size.z = 1;

        return size.x * size.y * size.z;
    }

    private void ReloadMinMax() {
        min = sub(this.center, this.extents);
        max = add(this.center, this.extents);
    }

    public float3 ClosestPoint(float3 point) {
        float3 closest = (float3) point.clone();
        closest.x = clamp(closest.x, min.x, max.x);
        closest.y = clamp(closest.y, min.y, max.y);
        closest.z = clamp(closest.z, min.z, max.z);
        return closest;
    }

    public boolean Contains(float3 point) {
        return point.x >= min.x && point.x <= max.x
            && point.y >= min.y && point.y <= max.y
            && point.z >= min.z && point.z <= max.z;
    }

    public void Encapsulate(float3 point) {
        SetMinMax(min(min, point), max(max, point));
    }

    public void Encapsulate(Bounds bounds) {
        SetMinMax(min(min, bounds.min), max(max, bounds.max));
    }

    public void Expand(float amount)
    {
        extents = add(extents, float3(amount, amount, amount));
        ReloadMinMax();
    }

    public void Expand(float3 amount)
    {
        extents = add(extents, amount);
        ReloadMinMax();
    }

    public Bounds Intersection(Bounds other) {
        // 相交部分的最小和最大坐标
        float minX = Math.max(this.GetMin().x, other.GetMin().x);
        float minY = Math.max(this.GetMin().y, other.GetMin().y);
        float minZ = Math.max(this.GetMin().z, other.GetMin().z);

        float maxX = Math.min(this.GetMax().x, other.GetMax().x);
        float maxY = Math.min(this.GetMax().y, other.GetMax().y);
        float maxZ = Math.min(this.GetMax().z, other.GetMax().z);

        // 如果没有重叠,返回null
        if (minX > maxX || minY > maxY || minZ > maxZ) return null;

        Bounds intersection = new Bounds();

        intersection.SetMinMax(float3(minX,minY,minZ),float3(maxX,maxY,maxZ));

        return intersection;
    }

    public float3 IntersectRay(Ray ray) {
        return Bounds.IntersectRayAABB(ray, this);
    }

    public float3 IntersectRay(Ray ray, float distance) {
        return Bounds.IntersectRayAABB(ray, distance, this);
    }

    public final boolean Intersects(Bounds bounds) {
        return
            this.GetMin().x <= bounds.GetMax().x &&
            this.GetMax().x >= bounds.GetMin().x &&
            this.GetMin().y <= bounds.GetMax().y &&
            this.GetMax().y >= bounds.GetMin().y &&
            this.GetMin().z <= bounds.GetMax().z &&
            this.GetMax().z >= bounds.GetMin().z;
    }

    public final void SetMinMax(float3 min, float3 max) {
        this.extents = mul(sub(max, min), 0.5f);
        this.center = add(min, this.extents);
        this.min = min;
        this.max = max;
    }

    /**
     * 两包围盒碰撞，计算碰撞点并排序
     * 返回两个碰撞数据结构体，分别是源包围盒的和目标包围盒的。
     * 若返回空，则包围盒无碰撞。
     */
    public static Collision[] Collision(Bounds srcBound, Bounds destBound){
        Bounds intersection = srcBound.Intersection(destBound);  // 计算重合部分的Bounds

        if (intersection != null && !all(equal(intersection.GetSize(), float3.zero())))  // 如果存在重合部分
        {

            Collision srcCollision = new Collision();
            Collision destCollision = new Collision();

            Collision[] collisions = new Collision[] { srcCollision, destCollision };

            List<ContactPoint> srcPoints = new ArrayList<>();
            List<ContactPoint> destPoints = new ArrayList<>();

            float3 srcCenter = srcBound.center;
            float3 destCenter = destBound.center;

            for (float3 point : GetCorners(intersection)) {

                float srcDistance = lengthsq(sub(point, srcBound.center));
                float destDistance = lengthsq(sub(point, destBound.center));

                ContactPoint srcPoint = new ContactPoint(point,sub(destCenter,point),srcDistance);
                ContactPoint destPoint = new ContactPoint(point,sub(srcCenter,point),destDistance);

                srcPoints.add(srcPoint);
                destPoints.add(destPoint);

            }

            srcCollision.contactCount = srcPoints.size();
            destCollision.contactCount = destPoints.size();

            srcPoints.sort((ContactPoint a,ContactPoint b) -> a.separation > b.separation ? 1 : 0);
            destPoints.sort((ContactPoint a,ContactPoint b) -> a.separation > b.separation ? 1 : 0);

            srcCollision.contacts = srcPoints.toArray(new ContactPoint[0]);
            destCollision.contacts = destPoints.toArray(new ContactPoint[0]);

            srcCollision.intersection = intersection;
            destCollision.intersection = intersection;

            return collisions;
        }
        return null;
    }

    public final float DistanceSq(float3 point) {
        return lengthsq(sub(max(abs(sub(point, center)), extents), extents));
    }

    public final float Distance(float3 point) {
        return length(sub(max(abs(sub(point, center)), extents), extents));
    }

    private static List<float3> GetCorners(Bounds bounds)
    {
        List<float3> points = new ArrayList<>();

        points.add(bounds.min);
        points.add(bounds.max);
        points.add(new float3(bounds.min.x, bounds.min.y, bounds.max.z));
        points.add(new float3(bounds.min.x, bounds.max.y, bounds.min.z));
        points.add(new float3(bounds.max.x, bounds.min.y, bounds.min.z));
        points.add(new float3(bounds.min.x, bounds.max.y, bounds.max.z));
        points.add(new float3(bounds.max.x, bounds.min.y, bounds.max.z));
        points.add(new float3(bounds.max.x, bounds.max.y, bounds.min.z));

        return points;
    }

    public float3 GetMin() {
        return (float3) min.clone();
    }

    public float3 GetMax() {
        return (float3) max.clone();
    }

    public float3 GetSize() {
        return mul(this.extents, 2f);
    }

    public void SetSize(float3 size) {
        this.extents = mul(size, 0.5f);
    }
}