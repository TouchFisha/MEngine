package touchfish.unit.system.render;

import touchfish.unit.object.component.Camera;
import touchfish.unit.system.GameEngine;
import touchfish.unit.object.LifecycleObject;
import touchfish.unit.object.component.render.Renderer;
import touchfish.unit.system.WindowSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;

public class RenderPanel extends JPanel {
    RenderPanel(int width, int height){
        this.setName("Renderer");
        this.setPreferredSize(new Dimension(width, height));
        this.setFont(new Font("黑体",Font.BOLD, WindowSettings.fontSize));
        this.setForeground(Color.lightGray);
        this.setLayout(null);
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Renderer.graphics = g;
        Renderer.graphics2D = (Graphics2D) g;
        //进行绘制
        try {
            //深度排序
            Object[] objs = GameEngine.objectMap.values().stream().sorted(
                    Comparator.comparingInt(o -> o.ZBuffer)
            ).toArray();
            //迭代
            for (Object o : objs) {
                LifecycleObject obj = (LifecycleObject) o;
                if (obj.active && obj.started && !obj.destroyed){
                    obj.Update();
                    obj.FixedUpdate();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}