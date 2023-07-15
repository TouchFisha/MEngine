package touchfish.unit.object.component.render;

import static touchfish.unit.math.math.*;

import touchfish.unit.math.*;
import touchfish.unit.object.component.Camera;
import touchfish.unit.object.component.Component;
import touchfish.unit.object.assets.Material;
import touchfish.unit.object.component.physics.Collider;
import touchfish.unit.system.Debug;
import touchfish.unit.system.event.KeyBehavior;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import java.util.List;

public abstract class Renderer extends Component {
    public static final boolean debug = true;
    public static Graphics graphics;
    public static Graphics2D graphics2D;
    public static Camera mainCamera;
    public static int3 cameraPositionPixel;
    public static int2 mouse;
    public static int2 screen;
    public static float3 screenf;
    public static KeyBehavior mouse1;
    public static KeyBehavior mouse2;
    public static List<Renderer> deepRender = new LinkedList<>();

    protected float4x4 worldToLocalMatrix;
    protected float4x4 localToWorldMatrix;
    protected Material material;
    protected int3 positionPixel;
    protected int3 scalePixel;
    protected AffineTransform graphicsTransform;
    @Override
    public void Update() {
        deepRender.add(this);
        UpdatePixelTransform();
        UpdateGraphicsTransform();

        if (debug) {
            graphics2D.setPaint(Color.red);
            graphics2D.drawRect(positionPixel.x, positionPixel.y, scalePixel.x, scalePixel.y);
            Collider collider = GetComponent(Collider.class);
            if (collider != null) {
                graphics2D.setPaint(Color.blue);
                graphics2D.drawRect((int) collider.bounds.center.x + 1, (int) collider.bounds.center.y + 1, (int) collider.bounds.GetSize().x, (int) collider.bounds.GetSize().y);
                graphics2D.setPaint(Color.green);
                graphics2D.drawRect((int) collider.bounds.GetMin().x + 2, (int) collider.bounds.GetMin().y + 2, (int) collider.bounds.GetSize().x, (int) collider.bounds.GetSize().y);
                graphics2D.setPaint(Color.yellow);
                graphics2D.drawRect((int) collider.bounds.GetMax().x + 3, (int) collider.bounds.GetMax().y + 3, (int) collider.bounds.GetSize().x, (int) collider.bounds.GetSize().y);
//                Debug.DrawRay(collider.bounds.center, float3(1,1,1));
//
//                if (collider.rigidbody!=null) {
//                    if (transform.getPosition() != null && collider.direction != null) {
//                        graphics2D.setPaint(Color.red);
//                        Debug.DrawRay(collider.bounds.center, collider.direction);
//                    }
//                }
            }

            graphics2D.setPaint(Color.white);


        }



    }
    protected void UpdatePixelTransform() {

        switch (transform.locateType) {
            case Origin:
                positionPixel = sub(mul(transform.getPosition(), getMaterial().getBasePixelScale()).toInt3(), cameraPositionPixel);
                break;
            case Screen:
                positionPixel = sub(mul(transform.getPosition(), screenf).toInt3(), cameraPositionPixel);
                break;
            case Object:
                positionPixel = sub(transform.getPosition().toInt3(), cameraPositionPixel);
                break;
            default:
                throw new IllegalStateException("Unexpected locateType: " + transform.locateType);
        }

        switch (transform.scaledType) {
            case Origin:
                scalePixel =  mul(transform.getScale(), getMaterial().getBasePixelScale()).toInt3();
                break;
            case Screen:
                scalePixel = mul(transform.getScale(), screenf).toInt3();
                break;
            case Object:
                scalePixel = transform.getScale().toInt3();
                break;
            default:
                throw new IllegalStateException("Unexpected scaledType: " + transform.locateType);
        }

        if (transform.alignCenter.x){
            positionPixel.x += scalePixel.x / 2;
        }
        if (transform.alignCenter.y){
            positionPixel.y += scalePixel.y / 2;
        }
        if (transform.alignCenter.z){
            positionPixel.z += scalePixel.z / 2;
        }
    }

    protected void UpdateGraphicsTransform() {
        graphicsTransform = new AffineTransform();
        float3 scale = float3((float)scalePixel.x / material.mainTexture.width, (float)scalePixel.y / material.mainTexture.height, 1f);
        float3 rotationCenter = float3(positionPixel.x + (float) material.mainTexture.width / 2f, positionPixel.y + (float) material.mainTexture.height / 2f, 1f);
        float3 translate = float3((float) positionPixel.x / scale.x, (float) positionPixel.y / scale.y, 1f);
        graphicsTransform.scale(scale.x,scale.y);
        graphicsTransform.rotate(transform.getRotation().z, rotationCenter.x, rotationCenter.y);
        graphicsTransform.translate(translate.x, translate.y);
    }

    public int3 getScalePixel() {
        return scalePixel;
    }

    public int3 getPositionPixel() {
        return positionPixel;
    }

    public float4x4 getWorldToLocalMatrix() {
        return worldToLocalMatrix;
    }

    public void setWorldToLocalMatrix(float4x4 worldToLocalMatrix) {
        this.worldToLocalMatrix = worldToLocalMatrix;
    }

    public float4x4 getLocalToWorldMatrix() {
        return localToWorldMatrix;
    }

    public void setLocalToWorldMatrix(float4x4 localToWorldMatrix) {
        this.localToWorldMatrix = localToWorldMatrix;
    }

    public Material getMaterial() {
        return material;
    }

    public Material setMaterial(Material material) {
        return this.material = material;
    }
}
