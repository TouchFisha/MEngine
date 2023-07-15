package touchfish;

import touchfish.unit.annotation.MEngineAwake;
import touchfish.unit.annotation.MEngineStart;
import touchfish.unit.system.Application;
import touchfish.unit.system.WindowSettings;
import touchfish.unit.system.render.RenderFrame;
import touchfish.unit.system.thread.RenderThread;

public class run {
    public static void main(String[] args) {
        start();
    }
    public static void start(){
        RenderFrame.getInstance().create(WindowSettings.width, WindowSettings.height);
        Application.init();
        Application.InvokeStaticMethodByAnnotation(MEngineAwake.class);
        RenderThread.getInstance().start();
        Application.InvokeStaticMethodByAnnotation(MEngineStart.class);
    }
    public static void exit(){
        RenderThread.getInstance().interrupt();
        Application.dispose();
        System.exit(0);
    }
}
