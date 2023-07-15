package touchfish.unit.object.assets;

import touchfish.unit.math.float3;
import touchfish.unit.math.math;
import touchfish.unit.object.component.render.Renderer;
import touchfish.unit.system.Resource;
import touchfish.unit.object.BaseObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Texture extends BaseObject {
    public int width,height;
    protected BufferedImage bufferedImage;
    protected Image image;
    protected ImageIcon imageIcon;
    public Texture(String path) {
        super();
        setImageIcon(Resource.Images.get(path));
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public Image getImage() {
        return image;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        image = imageIcon.getImage();
        bufferedImage = new BufferedImage(width = image.getWidth(null),height = image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D tempGraphics = bufferedImage.createGraphics();
        tempGraphics.drawImage(image, 0, 0, null);
        tempGraphics.dispose();

    }

    public float3 getBaseScreenScale() {
        return math.float3((float)width / (float)Renderer.screen.x, (float)height / (float)Renderer.screen.y, 1f);
    }
}
