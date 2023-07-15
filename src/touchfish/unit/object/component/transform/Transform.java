package touchfish.unit.object.component.transform;

import touchfish.unit.math.*;
import static touchfish.unit.math.math.*;
import touchfish.unit.object.component.Component;
import touchfish.unit.system.GameEngine;

import java.util.ArrayList;
import java.util.List;


public class Transform extends Component {

    private float3 position;
    private float3 localPosition;
    private float3 rotation;
    private float3 localRotation;
    private float3 scale;
    private float3 localScale;
    private List<Transform> children;
    public int deep;
    public Transform parent;
    public boolean3 alignCenter;
    public ScaledType scaledType = ScaledType.Origin;
    public ScaledType locateType = ScaledType.Object;

    public Transform() {
        super();
        transform = this;
        children = new ArrayList<>();
        position = float3();
        rotation = float3();
        alignCenter = boolean3(true);
        scale = float3.one();
        deep = 0;
        if (GameEngine.root == null || GameEngine.root.transform == null) {

        }else{
            SetParent(GameEngine.root.transform);
        }
    }
    public void Translate(float3 vector) {
        setPosition(math.add(getPosition(),vector));
    }
    public void Translate(float x, float y, float z) {
        setPosition(math.add(getPosition(), math.float3(x,y,z)));
    }
    public void Rotate(float3 euler) {
        setRotation(math.add(getRotation(), euler));
    }
    public void Rotate(float x, float y, float z) {
        setRotation(math.add(getRotation(), math.float3(x,y,z)));
    }
    public void Scale(float3 scale) {
        setScale(math.add(getScale(), scale));
    }
    public void Scale(float x, float y, float z) {
        setScale(math.add(getScale(), math.float3(x,y,z)));
    }
    public void SetParent(Transform transform) {
        if (transform.uuid == uuid)
            return;
        if (parent != null){
            parent.children.remove(this);
        }
        parent = transform;
        parent.children.add(this);
        RefreshDeep();
        setPosition(position);
        setScale(scale);
    }

    public void RefreshDeep(){
        if (parent != null){
            for (int i = 0; i < parent.children.size(); i++) {
                parent.children.get(i).deep=transform.deep+100+i;
            }
        }
        RefreshZBuffer();
    }
    public void RefreshZBuffer(){
        if (gameObject==null)return;
        int ZBuffer = deep + (int)position.z;
        gameObject.ZBuffer = ZBuffer;
        for (List<Component> list : gameObject.components.values()) {
            for (Component component : list) {
                component.ZBuffer = ZBuffer;
            }
        }
    }

    public void DivParent() {
        this.SetParent(GameEngine.root.transform);
    }

    public Transform getChild(int i) {
        return children.get(i);
    }

    public float3 getLocalPosition() {
        return localPosition;
    }

    public void setLocalPosition(float3 localPosition) {
        this.localPosition = localPosition;
        setPosition(math.add(parent.position,localPosition));
    }

    public float3 getLocalRotation() {
        return localRotation;
    }

    public void setLocalRotation(float3 localRotation) {
        this.localRotation = localRotation;
    }

    public float3 getPosition() {
        return position;
    }

    public void setPosition(float3 position) {
        float3 div = math.sub(position, this.position);
        this.position = position;
        RefreshZBuffer();
        localPosition = math.sub(position, parent.position);
        for (Transform child : children) {
            child.setPosition(math.add(child.getPosition(), div));
        }
    }

    public float3 getRotation() {
        return rotation;
    }

    public void setRotation(float3 rotation) {
        this.rotation = rotation;
    }

    public float3 getScale() {
        return scale;
    }

    public void setScale(float3 scale) {
        float3 div = math.div(scale, this.scale);
        this.scale = scale;
        localScale = math.div(scale, parent.scale);
        for (Transform child : children) {
            child.setScale(math.mul(child.getScale(), div));
        }
    }

    public float3 getLocalScale() {
        return localScale;
    }

    public void setLocalScale(float3 localScale) {
        this.localScale = localScale;
        setScale(math.mul(parent.scale,localScale));
    }

    @Override
    public void OnDestroy(){
        for (Transform child : new ArrayList<>(children)) {
            Destroy(child.gameObject);
        }
        if (parent != null) {
            parent.children.remove(this);
            RefreshDeep();
        }
        children = null;
    }
}
