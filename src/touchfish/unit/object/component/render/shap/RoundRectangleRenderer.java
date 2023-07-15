package touchfish.unit.object.component.render.shap;

import java.awt.geom.RoundRectangle2D;

public class RoundRectangleRenderer extends ShapRenderer {
    public RoundRectangleRenderer(){
        shape = new RoundRectangle2D.Double(120, 20, 250, 250, 45, 45);
    }
}
