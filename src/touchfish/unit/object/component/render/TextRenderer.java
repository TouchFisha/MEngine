package touchfish.unit.object.component.render;


import touchfish.unit.math.boolean3;
import touchfish.unit.math.math;
import touchfish.unit.system.WindowSettings;

public class TextRenderer extends Renderer {

    private static final int textWidth = 2, textHeight = 3;

    public String text;
    public boolean3 textAlignCenter;

    public TextRenderer(){
        super();
        text = "New Text";
        textAlignCenter = math.boolean3(true);
    }

    @Override
    public void Update() {
        super.Update();
        int textAdditionX = textAlignCenter.x ? text.length() * WindowSettings.fontSize / textWidth : 0;
        int textAdditionY = textAlignCenter.y ? WindowSettings.fontSize / textHeight : 0;
        graphics.drawString(text, positionPixel.x-textAdditionX, positionPixel.y+textAdditionY);
    }

}
