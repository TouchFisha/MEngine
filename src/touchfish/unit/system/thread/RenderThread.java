package touchfish.unit.system.thread;

import touchfish.unit.annotation.MEngineFixedUpdate;
import touchfish.unit.annotation.MEngineUpdate;
import touchfish.unit.math.math;
import touchfish.unit.object.component.Camera;
import touchfish.unit.object.component.physics.Physics;
import touchfish.unit.object.component.render.Renderer;
import touchfish.unit.system.Application;
import touchfish.unit.system.Coroutine;
import touchfish.unit.system.WindowSettings;
import touchfish.unit.system.event.Input;
import touchfish.unit.system.event.KeyBehavior;
import touchfish.unit.system.render.RenderFrame;

public class RenderThread extends Thread {

    public static RenderThread instance = new RenderThread();

    public static RenderThread getInstance() {
        return instance;
    }

    /**
     * Real time from start game
     */
    public static float ctime = 0;
    /**
     * Fixed Update Time
     */
    public static float ftime = 0;
    /**
     * Render Update Time
     */
    public static float utime = 0;
    public static long currentTimeMillis = 0;
    public static long startTimeMillis = 0;

    @Override
    public synchronized void start() {
        startTimeMillis = System.currentTimeMillis();
        super.start();
    }

    @Override
    public void run(){
        while (!isInterrupted()){
            //Update Time
            currentTimeMillis = System.currentTimeMillis();
            float startTime = (currentTimeMillis - startTimeMillis) / 1000.0f;
            ftime = startTime - ctime;
            utime = ftime;
            ctime = startTime;

            if (Application.active) {

                //Update User Input
                Input.Update();

                //Update Key Behavior
                for (Object o : KeyBehavior.keys.values().toArray()) {
                    KeyBehavior k = (KeyBehavior) o;
                    k.run();
                }

                Renderer.mouse = RenderFrame.mousePosition;
                Renderer.screen = math.int2(RenderFrame.getInstance().getWidth(), RenderFrame.getInstance().getHeight());
                Renderer.screenf = math.float3(Renderer.screen.x,Renderer.screen.y,1);
                if (Renderer.mainCamera == null || Renderer.mainCamera.equals(null)) Renderer.mainCamera = Camera.getMainCamera();
                if (Renderer.mainCamera != null && !Renderer.mainCamera.equals(null)) Renderer.cameraPositionPixel = math.mul(Renderer.mainCamera.transform.getPosition(), Renderer.screenf).toInt3();

                //Clear Deep Render Memory
                Renderer.deepRender.clear();
                //Update Frame And Objects
                RenderFrame.panel.repaint();

                //Update Static
                Application.InvokeStaticMethodByAnnotation(MEngineUpdate.class);
                Application.InvokeStaticMethodByAnnotation(MEngineFixedUpdate.class);

                //Update Physics
                Physics.Update();

                //Update Coroutine
                Coroutine.Update();
            }


            try {
                Thread.sleep(1000 / WindowSettings.FPS);
            } catch (InterruptedException e) {
                System.out.println("Thread Over At "+ctime+" :"+this);;
            }
        }
    }
}