package touchfish.unit.object.component.render.shap;

import java.awt.geom.Ellipse2D;

public class CircleRenderer extends ShapRenderer {
    public CircleRenderer(){
        shape = new Ellipse2D.Double(100, 100, 100, 100);
    }


}
