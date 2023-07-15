package touchfish.unit.system;

import touchfish.unit.math.float3;
import touchfish.unit.object.component.render.Renderer;

import java.awt.*;

import static touchfish.unit.math.math.*;

public class Debug {
    public static void DrawLine(float3 ori, float3 end, Color color){
        Renderer.graphics2D.setPaint(color);
        Renderer.graphics2D.drawLine((int) ori.x, (int) ori.y, (int) (end.x), (int) (end.y));
    }
    public static void DrawLine(float3 ori, float3 end){
        DrawLine(ori,end,Color.white);
    }
    public static void DrawRay(float3 position, float3 direction){
        DrawRay(position,direction,100000, Color.white);
    }
    public static void DrawRay(float3 position, float3 direction, float distance){
        DrawRay(position,direction,distance, Color.white);
    }
    public static void DrawRay(float3 position, float3 direction, float distance, Color color){
        Renderer.graphics2D.setPaint(color);
        direction = mul(normalize(direction), distance);
        Renderer.graphics2D.drawLine((int) position.x, (int) position.y, (int) (direction.x+position.x), (int) (direction.y +position.y));
    }
}