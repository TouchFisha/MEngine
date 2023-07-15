package touchfish.unit.object.assets;

import touchfish.unit.math.float3;
import touchfish.unit.math.int3;
import touchfish.unit.object.BaseObject;

import java.awt.*;

public class Material extends BaseObject {
    public Texture mainTexture;
    public Shader shader;
    public Color color;
    public Material(){
        super();
        mainTexture = new Texture("default");
        color = new Color(255,255,255,1);
    }
    public Material(String imgPath){
        super();
        mainTexture = new Texture(imgPath);
        color = new Color(255,255,255,1);
    }

    public float3 getBaseScreenScale() {
        return mainTexture.getBaseScreenScale();
    }
    public float3 getBasePixelScale() {
        return new float3(mainTexture.width, mainTexture.height, 1f);
    }
}
