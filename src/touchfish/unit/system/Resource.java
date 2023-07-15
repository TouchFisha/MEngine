package touchfish.unit.system;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Resource {
    public final static String PathWay = ".\\Resource\\";
    public static final class Images {
        public static String getPathWay(){
            return PathWay+"Images\\";
        }
        public static ImageIcon get(String name){
            return new ImageIcon(PathWay+"Images\\"+name+".png");
        }
        public static Image getFromFileIndex(String path, int index){
            File file = new File(PathWay+"Images\\"+path);
            try {
                BufferedImage buffImg = ImageIO.read(file.listFiles()[index]);
                return buffImg;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public static final class Audio {

    }
}