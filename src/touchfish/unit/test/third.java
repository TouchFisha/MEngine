package touchfish.unit.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

class Comp {
    public String name;
    public List<Comp> children;
    public Comp(String name) {
        this.name = name;
        children = new ArrayList<>();
    }
    public void send(String item){
        item = item+"=>"+name;
        System.out.println(item);
        for (Comp exam3node : children) {
            exam3node.send(item);
        }
    }
}

public class third {
    public static void main(String[] args) {
        Comp n0 = new Comp("北京城市学院");
        Comp n1 = new Comp("教务办公室");
        Comp n2 = new Comp("顺义校区");
        Comp n3 = new Comp("行政办公室");
        Comp n4 = new Comp("信息学部");
        Comp n5 = new Comp("经管学部");
        n0.children.add(n1);
        n0.children.add(n2);
        n0.children.add(n3);
        n2.children.add(n1);
        n2.children.add(n4);
        n2.children.add(n5);
        n2.children.add(n3);
        n4.children.add(n1);
        n4.children.add(n3);
        n5.children.add(n1);
        n5.children.add(n3);
        n0.send("公文");
    }
}


class Panel extends JPanel {
    public Panel(int width, int height){
        this.setName("");
        this.setPreferredSize(new Dimension(width, height));
        this.setFont(new Font("黑体",Font.BOLD,15));
        this.setForeground(Color.red);
        this.setLayout(null);
        this.setBackground(Color.green);
        this.setDoubleBuffered(true);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}

class ButtonPanel extends JPanel {
    public ButtonPanel(int width, int height){
        this.setName("");
        this.setPreferredSize(new Dimension(width, height));
        this.setFont(new Font("黑体",Font.BOLD,15));
        this.setForeground(Color.red);
        this.setLayout(null);
        this.setBackground(Color.red);
        this.setDoubleBuffered(true);
        JButton button1 = new JButton("click");
        button1.setBounds(100,50,100,30);
        add(button1);
        button1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.getInstance();
            }
        });
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
class ButtonFrame extends JFrame {
    public static Frame instance;
    public static JPanel panel;
    public void create(){
        this.setTitle("");
        this.setSize(500,500);
        setLocation(400,300);
        panel = new ButtonPanel(500,500);
        this.setContentPane(panel);
        this.setVisible(true);
    }
}

class Frame extends JFrame {
    private static Frame instance;
    public static JPanel panel;
    private Frame(){}
    public static Frame getInstance() {
        if (instance == null) {
            instance = new Frame();
            instance.createInstance();
        }
        return instance;
    }
    public void createInstance(){
        this.setTitle("单例");
        this.setSize(500,500);
        setLocation(400,300);
        panel = new Panel(500,500);
        this.setContentPane(panel);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        new ButtonFrame().create();

    }
}
interface Render {
    void erase();
    void draw();
}
class Factory{
    public static Render create(String name){
        Render res = null;
        if (name.equals("Circle")){
            res = new Circle();
        } else if (name.equals("Triangle")){
            res = new Triangle();
        } else if (name.equals("Rectangle")){
            res = new Rectangle();
        }
        return res;
    }

    public static void main(String[] args) {
        Render r0 = create("Circle");
        Render r1 = create("Triangle");
        Render r2 = create("Rectangle");
        r0.draw();
        r0.erase();
        r1.draw();
        r1.erase();
        r2.draw();
        r2.erase();
    }
}

class Circle implements Render{

    @Override
    public void erase() {
        System.out.println("擦除Circle");
    }

    @Override
    public void draw() {
        System.out.println("绘制Circle");
    }

}
class Triangle implements Render{

    @Override
    public void erase() {
        System.out.println("擦除Triangle");
    }

    @Override
    public void draw() {
        System.out.println("绘制Triangle");
    }

}
class Rectangle implements Render{

    @Override
    public void erase() {
        System.out.println("擦除Rectangle");
    }

    @Override
    public void draw() {
        System.out.println("绘制Rectangle");
    }

}