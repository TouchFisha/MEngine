package touchfish.unit.object.component.render.shap;

import touchfish.unit.object.component.render.Renderer;

import java.awt.*;

public class ShapRenderer extends Renderer {
    public Shape shape;
    @Override
    public void Update() {
        Graphics2D g = (Graphics2D) graphics;
        g.draw(shape);
    }
    public void erase(){
        active = false;
    }
    public void draw(){
        active = true;
    }
}
