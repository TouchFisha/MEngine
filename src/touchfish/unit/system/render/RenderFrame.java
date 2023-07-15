package touchfish.unit.system.render;

import touchfish.run;
import touchfish.unit.system.Resource;
import touchfish.unit.math.int2;
import touchfish.unit.math.math;
import touchfish.unit.system.WindowSettings;
import touchfish.unit.system.event.KeyBehavior;
import touchfish.unit.system.event.Input;

import javax.swing.*;
import java.awt.event.*;

public class RenderFrame extends JFrame {
    private static RenderFrame instance = new RenderFrame();
    public static JPanel panel;
    public static int scroll = 0,panelRot = 0;
    public static int2 mousePosition = math.int2();
    public static RenderFrame getInstance() {
        return instance;
    }
    public void create(int width, int height){
        this.setTitle(WindowSettings.title);
        this.setSize(width,height);
        setLocation((1920-width)/2,(1080-height)/2);
        panel = new RenderPanel(width,height);
        this.setContentPane(panel);
        this.setVisible(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                // e.getKeyCode()返回的是ASCII码值，将此作为key值，以避免和鼠标key值冲突
                Input.press(e.getKeyCode());
            }
            @Override
            public void keyReleased(KeyEvent e){
                Input.keyup(e.getKeyCode());
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Input.press(e.getButton());
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                Input.keyup(e.getButton());
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePosition.x = e.getX();
                mousePosition.y = e.getY()-30;
            }
        });
        this.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                scroll = e.getWheelRotation();
                panelRot += scroll * 22;
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                run.exit();
            }
        });

        this.setIconImage(Resource.Images.get("icon").getImage());

        this.requestFocus();
        this.requestFocusInWindow();

        // 鼠标图片
        // Toolkit tk=Toolkit.getDefaultToolkit();
        // Cursor cu = tk.createCustomCursor(Resource.Images.get("mouse").getImage(),new Point(10,10),"plane");
        // this.setCursor(cu);
    }

}
