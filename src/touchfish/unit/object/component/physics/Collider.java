package touchfish.unit.object.component.physics;

import static touchfish.unit.math.math.*;
import static touchfish.unit.math.math.lengthsq;

import cn.hutool.core.math.MathUtil;
import touchfish.unit.math.*;
import touchfish.unit.object.component.Component;
import touchfish.unit.object.component.render.Renderer;
import touchfish.unit.system.Debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Collider extends Component {
    public float friction = 1;
    public Rigidbody rigidbody;
    public int shapeCount = 0;
    public Bounds bounds = new Bounds();
    public PhysicsMaterial material;
    public float3 size = float3.one();
    public float3 offset = float3.zero();
    public float3 ClosestPoint(float3 position) {
        return bounds.ClosestPoint(position);
    }
    public boolean useObjectScale = true;
    @Override
    public void Init() {
        super.Init();
        Physics.colliderList.add(this);
    }

    @Override
    public void Logout() {
        super.Logout();
        Physics.colliderList.remove(this);
    }
    private float3 viewBoundCenterDiv;
    @Override
    public void FixedUpdate() {
        if (useObjectScale) {
            Renderer renderer = GetComponent(Renderer.class);
            if (renderer != null) {
                float3 sizePixel = new float3(renderer.getScalePixel());
                float3 resize = mul(sizePixel, size);
                float3 centerPixel = new float3(renderer.getPositionPixel());
                float3 recenter = add(add(centerPixel, div(sub(sizePixel, resize), 2f)), offset);
                viewBoundCenterDiv = sub(centerPixel, recenter);
                bounds = new Bounds(recenter, resize);
            } else {
                System.err.println("No Renderer Attached To Collider Found.");
            }
        }

        if (rigidbody == null) rigidbody = GetComponent(Rigidbody.class);
        if (rigidbody != null) {

            if (material != null) {
                friction = material.friction;
            }

            rigidbody.friction = friction;

        }
    }

    @Override
    public void OnCollisionStay(Collision collision) {
        if (rigidbody!=null) {
            //float3 magnitude = abs(collision.intersection.GetSize());
            float3 position = bounds.center;
            float3 collisionCenter = collision.intersection.center;
//            float3 direction = normalize(sub(position, collisionCenter));
            float3 div = sub(collision.intersection.GetMax(), collision.intersection.GetMin());
            float3 divAbs = abs(div);
            List<Float> divList = Arrays.asList(divAbs.x, divAbs.y, divAbs.z);
            float max0 = math.max(divList);
            int max0Index = divList.indexOf(max0);
            divList.remove(max0);
            float max1 = math.max(divList);
            int max1Index = divList.indexOf(max1);
            float3 dir = new float3();
            float3 posSubCenter = sub(position, collisionCenter);
            if (max0Index == 0) {
                if (max1Index == 1) {
                    dir.z = posSubCenter.z > 0 ? 1 : -1;
                } else if (max1Index == 2) {
                    
                }
            } else if (max0Index == 1) {
                if (max1Index == 0) {

                } else if (max1Index == 2) {

                }
            } else if (max0Index == 2) {
                if (max1Index == 0) {

                } else if (max1Index == 1) {

                }
            }
            float3 direction = normalize(sub(position, collisionCenter));
            float insideForce = collision.intersection.GetVolume();
//            float relativeForce = length(collision.relativeVelocity);
//            float realForce = length(rigidbody.velocity);

            float3 reverse = mul(direction, min((float) Math.pow(max(max(insideForce,length(project(rigidbody.velocity,direction))), 30f),0.83f),200f));
            reverse.z = 0;
            System.out.println(insideForce);
            rigidbody.velocity =  add(rigidbody.velocity, mul(reverse, 0.001f)) ;
            //rigidbody.AddForce(mul(reverse,0.016f));

        }
    }

    public float3 RayCaseBounds(Ray ray) {
        return bounds.IntersectRay(ray);
    }
    public float3 RayCaseBounds(Ray ray, float distance) {
        return bounds.IntersectRay(ray,distance);
    }
}
